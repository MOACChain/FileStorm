package main

import (
	"encoding/hex"
	"encoding/json"
	"errors"
	"flag"
	"fmt"
	"net/http"
	"strconv"

	validator "gopkg.in/validator.v2"
)

var log = setupLogging(true)

func init() {
	flag.StringVar(&listenAddressAndPort, "listen-host-port", "127.0.0.1:18080", "host:port, e.g. 127.0.0.1:18080")
	flag.StringVar(&redisHostPort, "redis-host-port", "localhost:6379", "host:port, e.g. 127.0.0.1:6379")
	flag.StringVar(&ipfsHostPort, "ipfs-host-port", "localhost:5001", "host:port, e.g. 127.0.0.1:5001")
	// 加合约 --sub-chain-base
}

// AccessType is the ipfs file access type
type AccessType int

// Read=0, Write=1, Delete=2, Verify=3
const (
	Read   AccessType = 0
	Write  AccessType = 1
	Remove AccessType = 2
	Verify AccessType = 3
)

// FileHash is the ipfs hash and the type
type FileHash struct {
	hashString string
	hashType   string
}

type ReadRequest struct {
	Filehash string `validate:"len=46,regexp=^[a-zA-Z0-9]*$"`
}

type ProxyReadRequest struct {
	Filehash     string `validate:"len=46,regexp=^[a-zA-Z0-9]*$"`
	ProxyAddress string
}

type WriteRequest struct {
	Filehash string `validate:"len=46,regexp=^[a-zA-Z0-9]*$"`
}

type ProxyWriteRequest struct {
	Filehash     string `validate:"len=46,regexp=^[a-zA-Z0-9]*$"`
	ProxyAddress string
}

type DeleteRequest struct {
	Filehash string `validate:"len=46,regexp=^[a-zA-Z0-9]*$"`
}

type VerifyRequest struct {
	Filehash string `validate:"len=46,regexp=^[a-zA-Z0-9]*$"`
	Offset   string `validate:"regexp=^[0-9]*$"`
}

type RestoreToLocalRequest struct {
	Filehash string `validate:"len=46,regexp=^[a-zA-Z0-9]*$"`
}

type DirectDownloadRequest struct {
	Filehash string `validate:"len=46,regexp=^[a-zA-Z0-9]*$"`
}

func readHandler(w http.ResponseWriter, r *http.Request) {
	// sample query:
	// curl "http://127.0.0.1:18080/ipfs/read?file_hash=QmTor1GsqZQwJdFoTYjAdEEjXDZgYDm1oc3Lj8waHUKRFN"
	fileHash := r.URL.Query().Get("file_hash")
	readRequest := ReadRequest{Filehash: fileHash}
	if errs := validator.Validate(readRequest); errs != nil {
		http.Error(w, fmt.Sprintf("Invalid file_hash parameter: %v", errs), 400)
		return
	}

	if err := AddTaskToQueue(IpfsReadQueueName, fileHash); err != nil {
		http.Error(w, "Can not enqueue read request.", 500)
		return
	}
}

func writeHandler(w http.ResponseWriter, r *http.Request) {
	// sample query:
	// curl "http://127.0.0.1:18080/ipfs/write?file_hash=QmTor1GsqZQwJdFoTYjAdEEjXDZgYDm1oc3Lj8waHUKRFN"
	fileHash := r.URL.Query().Get("file_hash")
	writeRequest := WriteRequest{Filehash: fileHash}
	if errs := validator.Validate(writeRequest); errs != nil {
		http.Error(w, fmt.Sprintf("Invalid file_hash parameter: %v", errs), 400)
		return
	}

	if err := AddTaskToQueue(IpfsWriteQueueName, fileHash); err != nil {
		http.Error(w, "Can not enqueue write request.", 500)
		return
	}
}

func deleteHandler(w http.ResponseWriter, r *http.Request) {
	// sample query:
	// curl "http://127.0.0.1:18080/ipfs/delete?file_hash=QmTor1GsqZQwJdFoTYjAdEEjXDZgYDm1oc3Lj8waHUKRFN"
	fileHash := r.URL.Query().Get("file_hash")
	deleteRequest := DeleteRequest{Filehash: fileHash}
	if errs := validator.Validate(deleteRequest); errs != nil {
		http.Error(w, fmt.Sprintf("Invalid file_hash parameter: %v", errs), 400)
		return
	}

	if err := AddTaskToQueue(IpfsDeleteQueueName, fileHash); err != nil {
		http.Error(w, "Can not enqueue delete request.", 500)
		return
	}
}

func verifyHandler(w http.ResponseWriter, r *http.Request) {
	// sample query:
	// curl "http://127.0.0.1:18080/verify?file_hash=QmTor1GsqZQwJdFoTYjAdEEjXDZgYDm1oc3Lj8waHUKRFN&offset=0"
	q := r.URL.Query()
	randomInt := q.Get("random_int")
	block_number:= q.Get("block_number")
	next_number := q.Get("next_number")

	// 需要拿到合约地址
	// 调用合约拿到合约里的fileHash[] fileHash是一个struct, 里面有被验证次数，给文件一个ID

	// 设计一个算法拿到fileHash[]中得随机一组文件。利用randomInt和被验证次数，随机得把没怎么被验证过的文件验证一边。
	// 再从这个文件中找到第next_number个字节往后数128个字节，得出哈希值。

	// 发一个交易，把结果写到子链链上。data： ‘shard ID: 哈希值’

	// verifyRequest := VerifyRequest{Filehash: fileHash, Offset: randomInt}
	// if errs := validator.Validate(verifyRequest); errs != nil {
	// 	http.Error(w, fmt.Sprintf("Invalid verify parameter: %v", errs), 400)
	// 	return
	// }

	// offset, errParse := strconv.ParseInt(verifyRequest.Offset, 10, 64)
	// if errParse != nil {
	// 	http.Error(w, "Can not parse offset value.", 400)
	// 	return
	// }

	// err, result := HandleIPFSVerify(fileHash, offset)
	// if err != nil {
	// 	http.Error(w, err.Error(), 404)
	// }
	// w.Write(result)
}

func catchHandler(w http.ResponseWriter, r *http.Request) {
	input := r.URL.Query().Get("input")

	switch fileHash, accessType, shardId, ipfsId, nil := decodeInput(input); accessType {
	case Remove:
		deleteRequest := DeleteRequest{Filehash: fileHash}
		if errs := validator.Validate(deleteRequest); errs != nil {
			http.Error(w, fmt.Sprintf("Invalid file_hash parameter: %v", errs), 400)
			return
		}

		if err := AddTaskToQueue(IpfsDeleteQueueName, fileHash); err != nil {
			http.Error(w, "Can not enqueue delete request.", 500)
			return
		}
	case Write:
		writeRequest := WriteRequest{Filehash: fileHash}
		if errs := validator.Validate(writeRequest); errs != nil {
			http.Error(w, fmt.Sprintf("Invalid file_hash parameter: %v", errs), 400)
			return
		}

		if err := AddTaskToQueue(IpfsWriteQueueName, fileHash); err != nil {
			http.Error(w, "Can not enqueue write request.", 500)
			return
		}
	case Read:
		readRequest := ReadRequest{Filehash: fileHash}
		if errs := validator.Validate(readRequest); errs != nil {
			http.Error(w, fmt.Sprintf("Invalid file_hash parameter: %v", errs), 400)
			return
		}

		if err := AddTaskToQueue(IpfsReadQueueName, fileHash); err != nil {
			http.Error(w, "Can not enqueue read request.", 500)
			return
		}
	}
}

// 新合约会要加上调用服务器的IPFS ADDR

// DecodeIpfsParams decodes input byte array and returns the value of ipfsFile parameters.
func decodeInput(hexString string) (string, AccessType, uint shardId, string ipfsId, error) {
	invalidIpfsHasErr := errors.New("invalid ipfs hash format")
	log.Debugf("hexString, |%v|", hexString)
	if len(hexString) < 266 {
		log.Errorf("Ipfs Input Code is too short. %v", hexString)
		return "", 0, invalidIpfsHasErr
	}
	log.Debugf("hexString, %v", hexString)

	funcCode := hexString[:8] //get function code
	if funcCode != "633fb659" {
		log.Errorf("Invalid ipfs function call. %v", funcCode)
		return "", 0, errors.New("invalid function call.")
	}
	hexString = hexString[8:] //remove function code "633fb659"

	hexString = hexString[64:] //remove first param "0000000000000000000000000000000000000000000000000000000000000020"

	accessTypeString := hexString[:64] //get access type
	log.Debugf("accessTypeString, %v", accessTypeString)
	accessType, _ := strconv.Atoi(accessTypeString)
	hexString = hexString[64:] //remove access type
	// accessModeString := hexString[:64] //get access mode
	// log.Debugf("accessModeString, %v", accessModeString)
	// hexString = hexString[64:] //remove access mode
	hexString = hexString[64:] //remove another parameter "000000000000000000000000000000000000000000000000000000000000002e"

	bs, err := hex.DecodeString(hexString)
	if err != nil {
		log.Errorf("Decode error: %v", err)
		return "", 0, err
	}
	fileHash := string(bs)
	if len(fileHash) < 46 {
		log.Errorf("Ipfs Hash is too short. %v", fileHash)
		return "", 0, invalidIpfsHasErr
	}
	fileHash = fileHash[0:46]
	log.Debugf("fileHash, %v", fileHash)
	if fileHash[0:2] != "Qm" {
		log.Debugf("hex |%v|", hexString[0:2])
		log.Errorf("Ipfs Hash is in wrong format. %v", fileHash)
		return "", 0, invalidIpfsHasErr
	}
	return fileHash, AccessType(accessType), nil
}

func proxyReadHandler(w http.ResponseWriter, r *http.Request) {
	// read through proxy(vnode) server
	originalFileHash := r.URL.Query().Get("file_hash")
	proxyAddress := r.URL.Query().Get("proxy_address")
	proxyReadRequest := ProxyReadRequest{
		Filehash:     originalFileHash,
		ProxyAddress: proxyAddress,
	}

	if errs := validator.Validate(proxyReadRequest); errs != nil {
		http.Error(w, fmt.Sprintf("Invalid parameter: %v", errs), 400)
		return
	}

	mProxyReadRequest, _ := json.Marshal(proxyReadRequest)
	if err := AddTaskToQueue(IpfsProxyReadQueueName, string(mProxyReadRequest)); err != nil {
		http.Error(w, "Can not enqueue proxy read request.", 500)
		return
	}
}

func proxyWriteHandler(w http.ResponseWriter, r *http.Request) {
	// write through proxy(vnode) server
	originalFileHash := r.URL.Query().Get("file_hash")
	proxyAddress := r.URL.Query().Get("proxy_address")
	proxyWriteRequest := ProxyWriteRequest{
		Filehash:     originalFileHash,
		ProxyAddress: proxyAddress,
	}

	if errs := validator.Validate(proxyWriteRequest); errs != nil {
		http.Error(w, fmt.Sprintf("Invalid parameter: %v", errs), 400)
		return
	}

	mProxyWriteRequest, _ := json.Marshal(proxyWriteRequest)
	if err := AddTaskToQueue(IpfsProxyWriteQueueName, string(mProxyWriteRequest)); err != nil {
		http.Error(w, "Can not enqueue proxy write request.", 500)
		return
	}

}

func main() {
	flag.Parse()

	// verify does not use queue and should return immediately the result
	http.HandleFunc("/verify", verifyHandler)
	// catch will determine what routing it should do.
	http.HandleFunc("/catch", catchHandler)

	// routing
	http.HandleFunc("/ipfs/read", readHandler)
	http.HandleFunc("/ipfs/write", writeHandler)
	http.HandleFunc("/ipfs/delete", deleteHandler)

	// // proxy read/write
	// http.HandleFunc("/proxy/read", proxyReadHandler)
	// http.HandleFunc("/proxy/write", proxyWriteHandler)

	// // vnode proxy endpoints
	// http.HandleFunc("/vnode/restoreToLocal", restoreToLocalHandler)
	// http.HandleFunc("/vnode/directDownload", directDownloadHandler)

	// print config
	log.Info("StormCatcher:", listenAddressAndPort)
	log.Info("Redis:", redisHostPort)
	log.Info("Ipfs:", ipfsHostPort)

	// init redis
	initRedisClient()
	// init queue and queue handler
	initThrottledQueues()
	log.Info("Channel setup done. Storm Catcher is ready!")
	initThrottledQueueHandlers()

	//init gc routine
	initGCWorker()

	//init unpin worker
	initUnpinWorker()

	// start server
	log.Critical(http.ListenAndServe(listenAddressAndPort, nil))
}
