package main

import (
	"bytes"
	"encoding/binary"
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"math/rand"
	"net/http"
	"os"
	"strings"
	"time"

	resty "gopkg.in/resty.v1"
)

func getRandomIntBuf() []byte {
	//random number
	s := rand.NewSource(time.Now().UnixNano())
	r := rand.New(s)
	bs := make([]byte, binary.MaxVarintLen64)
	binary.PutUvarint(bs, r.Uint64())

	// this is 10 bytes long, take the first 8
	return bs[0:8]
}

func checkoutIPFSFile(hash string) (*os.File, error) {
	// get file content from ipfs using cat endpoint
	url := fmt.Sprintf(
		"%s/%s?%s",
		fmt.Sprintf("http://%s", ipfsHostPort),
		"api/v0/cat",
		fmt.Sprintf("arg=/ipfs/%s", hash),
	)

	resp, err := http.Get(url)
	if err != nil {
		log.Error(err)
		return nil, err
	}
	defer resp.Body.Close()

	// create new tmp file and write content into it
	tmpfile, err := ioutil.TempFile("", IpfsPrefix)
	if err != nil {
		log.Fatal(err)
		return nil, err
	}
	io.Copy(tmpfile, resp.Body)
	return tmpfile, nil
}

func createAlteredTmpFile(tmpfile *os.File) *os.File {
	//create altered file
	tmpfile.Seek(0, 0)
	alteredTmpfile, err := ioutil.TempFile("", IpfsPrefix)
	var buffer = make([]byte, IpfsChunkSize-8)
	err = nil
	n := 0
	for {
		if err == io.EOF {
			break
		}

		n, err = tmpfile.Read(buffer)
		if n > 0 {
			rBuf := getRandomIntBuf()
			alteredTmpfile.Write(rBuf)
			alteredTmpfile.Write(buffer[:n])
		}
	}

	return alteredTmpfile
}

func createRestoredTmpFile(alteredTmpfile *os.File) *os.File {
	alteredTmpfile.Seek(0, 0)
	restoredTmpFile, err := ioutil.TempFile("", IpfsPrefix)
	var buffer = make([]byte, IpfsChunkSize)
	err = nil
	n := 0
	for {
		if err == io.EOF {
			break
		}
		n, err = alteredTmpfile.Read(buffer)
		if n > 0 {
			restoredTmpFile.Write(buffer[8:n])
		}
	}
	log.Info("Created restored file", restoredTmpFile.Name())
	return restoredTmpFile
}

type IpfsMkdirResponse struct {
	Message string
	Code    int64
	Type    string
}

func getDirNameFromOriginalFileHash(originalFileHash string) string {
	return originalFileHash[0:8]
}

func mkdirIpfsFile(originalFileHash string) (string, error) {
	// take the first 8 bytes as dir name
	dirName := getDirNameFromOriginalFileHash(originalFileHash)
	// make new dir in ipfs
	url := fmt.Sprintf(
		"%s/%s?%s",
		fmt.Sprintf("http://%s", ipfsHostPort),
		"api/v0/files/mkdir",
		fmt.Sprintf("arg=/%s", dirName),
	)
	resp, err := http.Get(url)
	if err != nil {
		log.Error(err)
		return "", err
	}
	defer resp.Body.Close()

	// handle response
	body, err := ioutil.ReadAll(resp.Body)
	var m IpfsMkdirResponse
	err = json.Unmarshal(body, &m)
	log.Info("ipfs mkdir", m.Code, m.Message, dirName)
	return dirName, nil
}

type IpfsAddResponse struct {
	Name string
	Hash string
	Size string
}

func AddIpfsFile(tmpFile *os.File) (string, error) {
	url := fmt.Sprintf(
		"%s/%s?%s",
		fmt.Sprintf("http://%s", ipfsHostPort),
		"api/v0/add",
		fmt.Sprintf(""),
	)

	tmpFileNames := strings.Split(tmpFile.Name(), "/")
	tmpFileName := tmpFileNames[len(tmpFileNames)-1]
	tmpFileBytes, _ := ioutil.ReadFile(tmpFile.Name())
	resp, err := resty.
		R().
		SetFileReader("file", tmpFileName, bytes.NewReader(tmpFileBytes)).
		SetFormData(map[string]string{}).
		Post(url)

	if err != nil {
		return "", err
	}

	var m IpfsAddResponse
	json.Unmarshal(resp.Body(), &m)
	log.Info("ipfs file added", m.Hash, m.Size)

	return m.Hash, nil
}

type IpfsCpResponse struct {
	Message string
	Code    int
	Type    string
}

func checkInIpfsFile(alteredTmpfile *os.File, originalFileHash string) error {
	// add altered file to ipfs
	alteredFileHash, _ := AddIpfsFile(alteredTmpfile)

	// if originalFileHash is "", it's not to checkin new written file
	// but to restore original file in ipfs.
	// No need to update mapping and run cp command
	if originalFileHash == "" {
		return nil
	}

	// update the redis hash mapping
	updateFileHashMapping(originalFileHash, alteredFileHash)

	// run a ipfs cp command to put the file in a directory for file/read later
	// this is because only files/read support offset and limit
	dirName, errMkdirIpfs := mkdirIpfsFile(originalFileHash)
	if errMkdirIpfs != nil {
		return errMkdirIpfs
	}
	url := fmt.Sprintf(
		"%s/%s?%s",
		fmt.Sprintf("http://%s", ipfsHostPort),
		"api/v0/files/cp",
		fmt.Sprintf("arg=/ipfs/%s&arg=/%s/%s", alteredFileHash, dirName, originalFileHash),
	)
	log.Info(url)

	resp, errFilesCp := resty.R().Get(url)
	if errFilesCp != nil {
		return errFilesCp
	}
	var m IpfsCpResponse
	json.Unmarshal(resp.Body(), &m)
	log.Info("ipfs cp cmd response:", m.Code, m.Message, m.Type)

	return nil
}

type IpfsUnpinResponse struct {
	Message string
	Code    int
	Type    string
}

func deleteIpfsFile(originalFileHash string, clearCache bool) error {
	// this actually unpin the object and wait for next gc to remove it from ipfs storage
	fileHashToUnpin := originalFileHash

	// if it's to delete altered file which is the 'real' delete instead of
	// clearing restored file during file read
	if !clearCache {
		fileHashToUnpin = GetAlteredFileHash(originalFileHash)
	}
	url := fmt.Sprintf(
		"%s/%s?%s",
		fmt.Sprintf("http://%s", ipfsHostPort),
		"api/v0/pin/rm",
		fmt.Sprintf("arg=/ipfs/%s&recursive=true", fileHashToUnpin),
	)

	log.Info(url)
	resp, err := resty.R().Get(url)
	if err != nil {
		return err
	}
	body := resp.Body()
	var m IpfsUnpinResponse
	json.Unmarshal(body, &m)
	log.Info(fmt.Sprintf("delete/unpin response, clearCache=%v: %s", clearCache, string(body)))

	return nil
}

func ipfsRepoGc() {
	url := fmt.Sprintf(
		"%s/%s",
		fmt.Sprintf("http://%s", ipfsHostPort),
		"api/v0/repo/gc?quiet=true&stream-errors=false",
	)

	_, err := resty.R().Get(url)
	if err != nil {
		log.Error("Ipfs RPC error", url, err)
	} else {
		log.Info("Ipfs RPC connected", url)
	}
}

func readIpfsFileChunk(originalFileHash string, offset int64, limit int64) []byte {
	filePath := fmt.Sprintf("/%s/%s", getDirNameFromOriginalFileHash(originalFileHash), originalFileHash)
	// curl "http://localhost:5001/api/v0/files/read?arg=/QmYcvhVaowRfwh88HRXm4a1Xj19h2CJ12yK4orK2bppj2L&offset=100&count=10"
	url := fmt.Sprintf(
		"%s/%s",
		fmt.Sprintf("http://%s", ipfsHostPort),
		fmt.Sprintf("api/v0/files/read?arg=%s&offset=%d&count=%d", filePath, offset, limit),
	)
	r, err := resty.R().Get(url)
	log.Info("verify url", url, err)
	if err != nil {
		return []byte{}
	} else {
		return []byte(r.Body())
	}
}

func isIpfsFilePined(fileHash string) bool {
	// curl "http://localhost:5001/api/v0/pin/ls?arg=/ipfs/QmZdA4wjEBgwTYWCJNnfVr474Fz3ueiqTr4fVTHGmnfF7j"
	url := fmt.Sprintf(
		"%s/%s",
		fmt.Sprintf("http://%s", ipfsHostPort),
		fmt.Sprintf("api/v0/pin/ls?arg=/ipfs/%s", fileHash),
	)
	r, err := resty.R().Get(url)
	log.Info("List pinned file url", url, err)
	if err != nil {
		return false
	} else {
		// Hash pinned. {"Keys":{"QmZdA4wjEBgwTYWCJNnfVr474Fz3ueiqTr4fVTHGmnfF7j":{"Type":"recursive"}}}
		// Hash not pinned. {"Message":"path '/ipfs/QmZdA4wjEBgwTYWCJNnfVr474Fz3ueiqTr4fVTHGmnfF7i' is not pinned","Code":0,"Type":"error"}
		_r := string(r.Body())
		return !strings.Contains(_r, "is not pinned")
	}
}
