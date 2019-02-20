package main

import (
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"os"
	"time"

	validator "gopkg.in/validator.v2"
)

var q2cMapping map[string](chan string)
var handlerMapping map[string]func(string) error

func handleIPFSRead(originalFileHash string) error {
	log.Info("inside read gorouting")

	// step 1
	alteredFileHash := GetAlteredFileHash(originalFileHash)

	// step 2
	alteredTmpfile, errCheckout := checkoutIPFSFile(alteredFileHash)
	if errCheckout != nil {
		return errCheckout
	}
	log.Info("Checkout altered file hash", alteredFileHash, "with file", alteredTmpfile.Name())
	defer os.Remove(alteredTmpfile.Name())

	// step 3
	restoredTmpFile := createRestoredTmpFile(alteredTmpfile)
	defer os.Remove(restoredTmpFile.Name())

	// step 4, originalFileHash set to "" means it's not to checkin new written file
	if errCheckInIpfs := checkInIpfsFile(restoredTmpFile, ""); errCheckInIpfs != nil {
		return errCheckInIpfs
	}

	// step 5, remember to unpin restored file
	tFuture := time.Now().Unix() + RestoredFileUnpinInterval
	unpinFileHashQueueAdd(originalFileHash, tFuture)

	log.Info("Ipfs read complete:", originalFileHash)

	return nil
}

func HandleIPFSWrite(fileHash string) error {
	log.Info("Inside write gorouting")

	// step 1, get the file from ipfs network
	log.Info("Downloading", fileHash, "...")
	tmpFile, errCheckout := checkoutIPFSFile(fileHash)
	if errCheckout != nil {
		return errCheckout
	}
	f, _ := tmpFile.Stat()
	stat := FileHashStat{Size: f.Size()}
	updateFileHashStat(fileHash, stat)
	log.Info("Downloaded file hash ", fileHash, "into file", tmpFile.Name())
	defer os.Remove(tmpFile.Name())

	// step 2, alter the file content for scs node security
	alteredTmpfile := createAlteredTmpFile(tmpFile)
	log.Info("Created alterd tmp file to", alteredTmpfile.Name())
	defer os.Remove(alteredTmpfile.Name())

	// step 3, check in the altered file into ipfs network
	if errCheckInIpfs := checkInIpfsFile(alteredTmpfile, fileHash); errCheckInIpfs != nil {
		return errCheckInIpfs
	}
	log.Info("Ipfs write complete:", fileHash)

	return nil
}

func runIpfsGC() {
	ipfsRepoGc()
}

func HandleIPFSDelete(fileHash string) error {
	log.Info("inside delete gorouting")

	// false means we actually want to delete a file instead of just clear a cached checkout file
	if err := deleteIpfsFile(fileHash, false); err != nil {
		log.Info("Ipfs delete interrupted:", fileHash)
		return err
	}
	log.Info("Ipfs delete complete:", fileHash)

	if isIpfsFilePined(fileHash) {
		// if there is a cached file, make it unpin now
		tFuture := time.Now().Unix()
		unpinFileHashQueueAdd(fileHash, tFuture)
	}

	if err := deleteFileHashStat(fileHash); err != nil {
		log.Info("Can not remove file hash from redis stat set")
		return err
	}
	log.Info("Removed file hash from redis stat set")

	if err := deleteFileHashMappings(fileHash); err != nil {
		log.Info("Can not remove file hash from redis hash mappings")
		return err
	}
	log.Info("Removed file hash from redis hash mappings")

	return nil
}

// offset就是nextNumber

func getVerifyOffsetAndLength(fileSize int64, offset int64) ([]int64, bool) {
	if fileSize == 0 {
		return []int64{0, 0, -1, -1}, false
	}

	// determine read start and end
	start := offset % fileSize
	offset = start
	end := (offset + ipfsVerifyReadLength) % fileSize
	// read until the end of the file if end is too large
	if end < start {
		end = fileSize - 1
	}

	startChunkIndex := start / (IpfsChunkSize - 8)
	startChunkOffset := start % (IpfsChunkSize - 8)
	endChunkIndex := end / (IpfsChunkSize - 8)

	ret := []int64{-1, -1, -1, -1}
	if startChunkIndex == endChunkIndex {
		// if read content within one chunk
		// call go file read once with
		mOffset := startChunkIndex*IpfsChunkSize + 8 + startChunkOffset
		mLength := ipfsVerifyReadLength
		if mLength > (fileSize - offset) {
			mLength = fileSize - offset
		}
		ret[0] = mOffset
		ret[1] = mLength
		return ret, false
	} else {
		// if read content across two chunks
		// need to call go file read twice

		// read until end of the first chunk
		mOffset := startChunkIndex*IpfsChunkSize + 8 + startChunkOffset
		mLength := (startChunkIndex+1)*IpfsChunkSize - mOffset
		ret[0] = mOffset
		ret[1] = mLength
		// read content from second chunk
		nOffset := endChunkIndex*IpfsChunkSize + 8
		nLength := ipfsVerifyReadLength - mLength
		ret[2] = nOffset
		ret[3] = nLength
		return ret, true
	}
}

var HandleIPFSVerify = func(originalFileHash string, offset int64) (error, []byte) {
	// read ipfsVerifyReadLength bytes from the file
	// if file length is less then length byte
	// read the bytes needed in rotate
	log.Info("inside verify gorouting")
	err, stat := getFileHashStat(originalFileHash)
	if err != nil {
		return err, []byte{}
	}

	// deal with file with size 0
	if stat.Size == 0 {
		return nil, []byte{}
	}

	ret, isStraddle := getVerifyOffsetAndLength(stat.Size, offset)
	if isStraddle {
		mOffset := ret[0]
		mLength := ret[1]
		mBytes := readIpfsFileChunk(originalFileHash, mOffset, mLength)
		log.Info(fmt.Sprintf("verify read 1 %s %d %d", originalFileHash, mOffset, mLength), len(mBytes))
		nOffset := ret[2]
		nLength := ret[3]
		nBytes := readIpfsFileChunk(originalFileHash, nOffset, nLength)
		log.Info(fmt.Sprintf("verify read 2 %s %d %d", originalFileHash, nOffset, nLength), len(nBytes))
		return nil, append(mBytes, nBytes...)
	} else {
		mOffset := ret[0]
		mLength := ret[1]
		mBytes := readIpfsFileChunk(originalFileHash, mOffset, mLength)
		log.Info(fmt.Sprintf("verify read 1 %s %d %d", originalFileHash, mOffset, mLength), len(mBytes))
		return nil, mBytes
	}
}

func handleIPFSProxyRead(mProxyReadRequest string) error {
	proxyReadRequest := new(ProxyReadRequest)
	json.Unmarshal([]byte(mProxyReadRequest), &proxyReadRequest)

	originalFileHash := proxyReadRequest.Filehash
	proxyAddress := proxyReadRequest.ProxyAddress
	log.Info("In handle ipfs proxy read: ", originalFileHash, proxyAddress)

	// call proxy to restore altered file locally on proxy
	alteredFileHash := GetAlteredFileHash(originalFileHash)
	restoreToLocalURL := fmt.Sprintf(
		"%s/%s?%s",
		fmt.Sprintf("http://%s", proxyAddress),
		"vnode/restoreToLocal",
		fmt.Sprintf("file_hash=%s", alteredFileHash),
	)

	resp, err := http.Get(restoreToLocalURL)
	if err != nil || resp.StatusCode != 200 {
		log.Error("Can not restore file, original hash ", originalFileHash, "altered hash ", alteredFileHash)
	}

	return nil
}

func handleIPFSProxyWrite(mProxyWriteRequest string) error {
	proxyWriteRequest := new(ProxyWriteRequest)
	json.Unmarshal([]byte(mProxyWriteRequest), &proxyWriteRequest)

	originalFileHash := proxyWriteRequest.Filehash
	proxyAddress := proxyWriteRequest.ProxyAddress
	log.Info("In handle ipfs proxy read: ", originalFileHash, proxyAddress)

	// step 1, direct download file from proxy
	directDownloadURL := fmt.Sprintf(
		"%s/%s?%s",
		fmt.Sprintf("http://%s", proxyAddress),
		"vnode/directDownload",
		fmt.Sprintf("file_hash=%s", originalFileHash),
	)

	log.Info("Downloading", originalFileHash, "...")
	resp, _ := http.Get(directDownloadURL)
	tmpFile, _ := ioutil.TempFile("", IpfsPrefix)
	io.Copy(tmpFile, resp.Body)
	f, _ := tmpFile.Stat()
	stat := FileHashStat{Size: f.Size()}
	updateFileHashStat(originalFileHash, stat)
	log.Info("Downloaded file hash ", originalFileHash, "into file", tmpFile.Name())
	defer os.Remove(tmpFile.Name())

	// step 2, alter the file content for scs node security
	alteredTmpfile := createAlteredTmpFile(tmpFile)
	log.Info("Created alterd tmp file to", alteredTmpfile.Name())
	defer os.Remove(alteredTmpfile.Name())

	// step 3, check in the altered file into ipfs network
	if errCheckInIpfs := checkInIpfsFile(alteredTmpfile, originalFileHash); errCheckInIpfs != nil {
		return errCheckInIpfs
	}
	log.Info("Ipfs write complete:", originalFileHash)

	return nil
}

// Handle fs proxy read
func restoreToLocalHandler(w http.ResponseWriter, r *http.Request) {
	// checkout the altered file, restore it and add the restored file to ipfs
	originalFileHash := r.URL.Query().Get("file_hash")
	restoreToLocalRequest := RestoreToLocalRequest{Filehash: originalFileHash}
	if errs := validator.Validate(restoreToLocalRequest); errs != nil {
		http.Error(w, fmt.Sprintf("Invalid file_hash parameter: %v", errs), 400)
		return
	}

	go handleRestoreToLocal(restoreToLocalRequest.Filehash)
}

// Handle fs proxy write
func directDownloadHandler(w http.ResponseWriter, r *http.Request) {
	// checkout the altered file, restore it and add the restored file to ipfs
	originalFileHash := r.URL.Query().Get("file_hash")
	directDownloadRequest := DirectDownloadRequest{Filehash: originalFileHash}
	if errs := validator.Validate(directDownloadRequest); errs != nil {
		http.Error(w, fmt.Sprintf("Invalid file_hash parameter: %v", errs), 400)
		return
	}

	url := fmt.Sprintf(
		"%s/%s?%s",
		fmt.Sprintf("http://%s", ipfsHostPort),
		"api/v0/cat",
		fmt.Sprintf("arg=/ipfs/%s", originalFileHash),
	)
	log.Infof("Direct download file %s with ipfs call: %s", originalFileHash, url)
	resp, err := http.Get(url)
	if err != nil {
		log.Errorf("%v", err)
	}
	defer resp.Body.Close()
	io.Copy(w, resp.Body)
	return
}

func handleRestoreToLocal(alteredFileHash string) {
	// step 1
	alteredTmpfile, errCheckout := checkoutIPFSFile(alteredFileHash)
	if errCheckout != nil {
		log.Errorf("Check out ipfs file error: %v, %s", errCheckout, alteredFileHash)
	}
	log.Infof("Checkout altered file hash %s with tmp file %s", alteredFileHash, alteredTmpfile.Name())
	defer os.Remove(alteredTmpfile.Name())

	// step 2
	restoredTmpFile := createRestoredTmpFile(alteredTmpfile)
	defer os.Remove(restoredTmpFile.Name())

	// step 3, originalFileHash set to "" means it's not to checkin new written file
	if _, errAddIpfsFile := AddIpfsFile(restoredTmpFile); errAddIpfsFile != nil {
		log.Errorf("Add restored file to ipfs error: %v, %s", errAddIpfsFile, restoredTmpFile.Name())
	}
}

func initThrottledQueues() {
	q2cMapping = make(map[string](chan string))
	queueNames := []string{
		IpfsReadQueueName,
		IpfsWriteQueueName,
		IpfsDeleteQueueName,
		IpfsProxyWriteQueueName,
		IpfsProxyReadQueueName,
	}

	// this create multiple goroutine constantly push new tasks into queue
	// push rate is throttled by queueConcurrency(=10)
	for _, queueName := range queueNames {
		c := make(chan string, queueConcurrency)
		q2cMapping[queueName] = c
		go func(queueName string, _c chan string) {
			log.Info("q2c mapping", queueName, "->", _c)
			for {
				// timeout is zero, so this will block on new tasks
				newTask, err := getTaskFromQueueBlock(queueName)
				if err != nil {
					log.Error("Can't connect to redis", redisHostPort, err)
					time.Sleep(time.Duration(1) * time.Second)
				} else {
					log.Info("Enqueue new task in", queueName, newTask)
					_c <- newTask
				}
			}
		}(queueName, c)
	}
}

func initThrottledQueueHandlers() {
	// init handler mapping
	handlerMapping = make(map[string]func(string) error)
	handlerMapping[IpfsReadQueueName] = handleIPFSRead
	handlerMapping[IpfsWriteQueueName] = HandleIPFSWrite
	handlerMapping[IpfsDeleteQueueName] = HandleIPFSDelete
	handlerMapping[IpfsProxyReadQueueName] = handleIPFSProxyRead
	handlerMapping[IpfsProxyWriteQueueName] = handleIPFSProxyWrite

	queueNames := []string{
		IpfsReadQueueName,
		IpfsWriteQueueName,
		IpfsDeleteQueueName,
		IpfsProxyWriteQueueName,
		IpfsProxyReadQueueName,
	}
	for _, queueName := range queueNames {
		go func(queueName string) {
			c := q2cMapping[queueName]
			log.Info("c from q:", queueName, c)
			for {
				// block on next task
				task := <-c
				log.Info(fmt.Sprintf("call %s handler with %v", queueName, task))
				err := handlerMapping[queueName](task)
				if err != nil {
					log.Info("Can't handl task, error:", err)
				}
			}
		}(queueName)
	}
}

func initGCWorker() {
	// run ipfs repo gc periodically
	go func() {
		for {
			runIpfsGC()
			time.Sleep(time.Duration(ipfsGCInterval) * time.Second)
		}
	}()
}

func runIpfsUnpin() {
	tNow := time.Now().Unix()
	fileHashes, err := unpinFileHashQueueRange(int64(0), tNow)
	if err == nil {
		for _, fileHash := range fileHashes {
			// true means it's a delete to clear restored file
			err := deleteIpfsFile(fileHash, true)
			if err != nil {
				log.Info("Failed to clear cache for file", fileHash)
			} else {
				log.Info("Unpined cache for file", fileHash)
				// remove the file hash from unpin queue
				unpinFileHashQueueRemove(fileHash)
			}
		}
	} else {
		log.Info("Failed getting unpin queue.")
	}
}

func initUnpinWorker() {
	// run checkout file clearance periodically
	go func() {
		for {
			runIpfsUnpin()
			time.Sleep(time.Duration(ipfsUnpinInterval) * time.Second)
		}
	}()
}
