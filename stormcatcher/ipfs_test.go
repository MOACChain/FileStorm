package main

import (
	"io/ioutil"
	"os"
	"testing"

	. "github.com/smartystreets/goconvey/convey"
)

func generateTestFileContent(n int) []byte {
	index := [10]byte{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}
	fileContent := make([]byte, n)
	for i := 0; i < n; i++ {
		x := i % 10
		fileContent[i] = index[x]
	}

	return fileContent
}

func generateTestFile(n int) *os.File {
	originalFile, _ := ioutil.TempFile("", "")
	fileContent := generateTestFileContent(n)
	originalFile.Write(fileContent)
	return originalFile
}

func TestIPFSMonkeyIPFSTest(t *testing.T) {
	Convey("Test file alter and restore", t, func() {
		Convey("restore file should be the same as the original", func() {
			// setup test file
			originalFile := generateTestFile(int(IpfsChunkSize + 1))

			// call alter file func
			alteredTmpfile := createAlteredTmpFile(originalFile)

			// call restore file func
			restoredTmpFile := createRestoredTmpFile(alteredTmpfile)

			originalFile.Seek(0, 0)
			restoredTmpFile.Seek(0, 0)

			readBufferOriginal := make([]byte, IpfsChunkSize+10)
			readBufferRestored := make([]byte, IpfsChunkSize+10)

			nReadOriginal, _ := originalFile.Read(readBufferOriginal)
			nReadRestored, _ := restoredTmpFile.Read(readBufferRestored)

			So(nReadOriginal == nReadRestored, ShouldBeTrue)
			bufferNoDiff := true
			for i := 0; i < nReadOriginal; i++ {
				if readBufferOriginal[i] != readBufferRestored[i] {
					bufferNoDiff = false
				}
			}
			So(bufferNoDiff, ShouldBeTrue)
		})

		Convey("altered file should be inserted with random string of 8 bytes", func() {
			// setup test file with length of chunk size + 1
			originalFile := generateTestFile(int(IpfsChunkSize + 1))

			// call alter file func
			alteredTmpfile := createAlteredTmpFile(originalFile)

			// read original first 16 bytes
			originalFile.Seek(0, 0)
			readBufferOriginal := make([]byte, 16)
			nReadOriginal, _ := originalFile.Read(readBufferOriginal)
			// read alter 8 to 24 which is first 16 bytes of original file
			alteredTmpfile.Seek(8, 0)
			readBufferAltered := make([]byte, 16)
			nReadAltered, _ := alteredTmpfile.Read(readBufferAltered)

			So(nReadAltered == 16, ShouldBeTrue)
			So(nReadOriginal == 16, ShouldBeTrue)
			for i := 0; i < 16; i++ {
				So(readBufferAltered[i] == readBufferOriginal[i], ShouldBeTrue)
			}

			// read original last 9 bytes
			originalFile.Seek(-9, 2)
			readBufferOriginal = make([]byte, 16)
			nReadOriginal, _ = originalFile.Read(readBufferOriginal)
			// read alter 8 to 24 which is first 16 bytes of original file
			alteredTmpfile.Seek(-9, 2)
			readBufferAltered = make([]byte, 16)
			nReadAltered, _ = alteredTmpfile.Read(readBufferAltered)
			So(nReadAltered == 9, ShouldBeTrue)
			So(nReadOriginal == 9, ShouldBeTrue)
			bufferNoDiff := true
			for i := 0; i < 9; i++ {
				if readBufferAltered[i] != readBufferOriginal[i] {
					bufferNoDiff = false
				}
			}
			So(bufferNoDiff, ShouldBeTrue)
			So(string(readBufferAltered[0:9]) == "678901234", ShouldBeTrue)
		})
	})
}
