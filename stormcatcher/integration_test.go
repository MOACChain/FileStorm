package main

import (
	"io/ioutil"
	"os"
	"testing"
	"time"

	. "github.com/prashantv/gostub"
	. "github.com/smartystreets/goconvey/convey"
)

func TestMonkeyCRUD(t *testing.T) {
	Convey("Test Monkey CRUD operations", t, func() {
		// setup
		initRedisClient()
		// init queue and queue handler
		initThrottledQueues()
		initThrottledQueueHandlers()
		//init gc routine
		initGCWorker()

		Convey("Test Monkey write", func() {
			tmpFilesBefore, _ := ioutil.ReadDir(os.TempDir())
			tmpFilesLengthBefore := len(tmpFilesBefore)
			// generate a new file and add it to ipfs for test
			fileSize := 20000000 // about 20m
			tmpFile := generateTestFile(fileSize)
			defer os.Remove(tmpFile.Name())
			fileHash, _ := AddIpfsFile(tmpFile)
			tmpFile.Close()
			HandleIPFSWrite(fileHash)

			//verify redis mappings and stats
			alteredFileHash, _ := redisClient.HGet(IpfsFileHashMappingName, fileHash).Result()
			So(alteredFileHash != "", ShouldBeTrue)
			originalFileHash, _ := redisClient.HGet(IpfsFileHashMappingName, alteredFileHash).Result()
			So(originalFileHash == fileHash, ShouldBeTrue)
			_, fileHashStat := getFileHashStat(fileHash)
			So(fileHashStat.Size == int64(fileSize), ShouldBeTrue)

			//check if tmp file get deleted
			tmpFilesAfter, _ := ioutil.ReadDir(os.TempDir())
			tmpFilesLengthAfter := len(tmpFilesAfter)
			// the only additional tmp file is the original file generated at the beginning of the test
			So(tmpFilesLengthAfter == tmpFilesLengthBefore+1, ShouldBeTrue)
		})

		Convey("Test Monkey delete", func() {
			// generate a new file and add it to ipfs for test
			fileSize := 20000000 // about 20m
			fileContent := make([]byte, fileSize)
			tmpFile, _ := ioutil.TempFile("", "ipfs_monkey_test_")
			defer os.Remove(tmpFile.Name())
			tmpFile.Write(fileContent)
			fileHash, _ := AddIpfsFile(tmpFile)
			tmpFile.Close()
			HandleIPFSWrite(fileHash)
			//verify redis mappings and stats
			alteredFileHash, _ := redisClient.HGet(IpfsFileHashMappingName, fileHash).Result()

			//reset unpin interval to 1 second
			stubs := Stub(&RestoredFileUnpinInterval, int64(1))
			defer stubs.Reset()
			//read the file
			handleIPFSRead(fileHash)

			// both altered file hash and original file hash should be unpin now
			So(isIpfsFilePined(fileHash), ShouldBeTrue)
			So(isIpfsFilePined(alteredFileHash), ShouldBeTrue)

			// delete the file
			HandleIPFSDelete(fileHash)
			// sleep for 2 seconds then run unpin
			time.Sleep(time.Duration(2) * time.Second)
			runIpfsUnpin()

			// both altered file hash and original file hash should be unpin now
			So(isIpfsFilePined(fileHash), ShouldBeFalse)
			So(isIpfsFilePined(alteredFileHash), ShouldBeFalse)

			//verify redis mappings and stats are deleted as well
			alteredFileHashAfterDelete, _ := redisClient.HGet(IpfsFileHashMappingName, fileHash).Result()
			So(alteredFileHashAfterDelete == "", ShouldBeTrue)
			originalFileHashAfterDelete, _ := redisClient.HGet(IpfsFileHashMappingName, alteredFileHash).Result()
			So(originalFileHashAfterDelete == "", ShouldBeTrue)
			_, fileHashStat := getFileHashStat(fileHash)
			So(fileHashStat == nil, ShouldBeTrue)
		})

		Convey("Test Monkey verify", func() {

			// generate a new file and add it to ipfs for test
			fileSize := 20000000 // about 20m
			fileContent := make([]byte, fileSize)
			tmpFile, _ := ioutil.TempFile("", "ipfs_monkey_test_")
			defer os.Remove(tmpFile.Name())
			tmpFile.Write(fileContent)
			fileHash, _ := AddIpfsFile(tmpFile)
			tmpFile.Close()
			HandleIPFSWrite(fileHash)

			_, verifyBytes256 := HandleIPFSVerify(fileHash, 100)
			So(len(verifyBytes256) == 256, ShouldBeTrue)
			_, verifyBytes128 := HandleIPFSVerify(fileHash, int64(fileSize-128))
			So(len(verifyBytes128) == 128, ShouldBeTrue)
		})

		Convey("Test Monkey read", func() {
			// generate a new file and add it to ipfs for test
			fileSize := 20000000 // about 20m
			fileContent := make([]byte, fileSize)
			tmpFile, _ := ioutil.TempFile("", "ipfs_monkey_test_")
			defer os.Remove(tmpFile.Name())
			tmpFile.Write(fileContent)
			fileHash, _ := AddIpfsFile(tmpFile)
			tmpFile.Close()
			HandleIPFSWrite(fileHash)

			handleIPFSRead(fileHash)
			score, _ := unpinFileHashQueueGet(fileHash)
			So(int64(score) > time.Now().Unix(), ShouldBeTrue)
		})

		Convey("Test clear restored file after n seconds", func() {
			fileSize := 20000000 // about 20m

			// file1, generate a new file and add it to ipfs for test
			tmpFile1 := generateTestFile(fileSize)
			defer os.Remove(tmpFile1.Name())
			fileHash1, _ := AddIpfsFile(tmpFile1)
			tmpFile1.Close()
			HandleIPFSWrite(fileHash1)

			// file2, generate a new file and add it to ipfs for test
			tmpFile2 := generateTestFile(fileSize + 1)
			defer os.Remove(tmpFile2.Name())
			fileHash2, _ := AddIpfsFile(tmpFile2)
			tmpFile2.Close()
			HandleIPFSWrite(fileHash2)

			// file3, generate a new file and add it to ipfs for test
			tmpFile3 := generateTestFile(fileSize + 2)
			defer os.Remove(tmpFile3.Name())
			fileHash3, _ := AddIpfsFile(tmpFile3)
			tmpFile3.Close()
			HandleIPFSWrite(fileHash3)

			//reset unpin interval to 1 second
			stubs := Stub(&RestoredFileUnpinInterval, int64(1))
			defer stubs.Reset()
			tNow := time.Now().Unix()
			// read it once and it should be pinned
			handleIPFSRead(fileHash1)
			handleIPFSRead(fileHash2)
			handleIPFSRead(fileHash3)

			// 3 files need to be unpinned in the future
			fileHashes, _ := unpinFileHashQueueRange(tNow, tNow+1000)
			So(len(fileHashes) == 3, ShouldBeTrue)

			So(isIpfsFilePined(fileHash1), ShouldBeTrue)
			So(isIpfsFilePined(fileHash2), ShouldBeTrue)
			So(isIpfsFilePined(fileHash3), ShouldBeTrue)

			// sleep for 2 seconds then run unpin
			time.Sleep(time.Duration(2) * time.Second)
			runIpfsUnpin()
			So(isIpfsFilePined(fileHash1), ShouldBeFalse)
			So(isIpfsFilePined(fileHash2), ShouldBeFalse)
			So(isIpfsFilePined(fileHash3), ShouldBeFalse)

			// No file needs to be unpinned in the future
			tNow = time.Now().Unix()
			fileHashes, _ = unpinFileHashQueueRange(tNow, tNow+10)
			So(len(fileHashes) == 0, ShouldBeTrue)
		})
	})
}
