package main

import (
	"testing"

	. "github.com/prashantv/gostub"
	. "github.com/smartystreets/goconvey/convey"
)

func TestCalculateVerifyOffsetAndLength(t *testing.T) {
	Convey("Test calculate verify offset and length", t, func() {
		Convey("Handle straddle case 1", func() {
			stubs := Stub(&ipfsVerifyReadLength, int64(15))
			stubs.Stub(&IpfsChunkSize, int64(100))
			defer stubs.Reset()
			fileSize := int64(1000)
			offset := int64(632)
			ret, isStraddle := getVerifyOffsetAndLength(fileSize, offset)
			So(isStraddle, ShouldBeTrue)
			So(ret[0] == 688, ShouldBeTrue)
			So(ret[1] == 12, ShouldBeTrue)
			So(ret[2] == 708, ShouldBeTrue)
			So(ret[3] == 3, ShouldBeTrue)
		})

		Convey("Handle straddle case 2", func() {
			stubs := Stub(&ipfsVerifyReadLength, int64(12))
			stubs.Stub(&IpfsChunkSize, int64(100))
			defer stubs.Reset()
			fileSize := int64(1000)
			offset := int64(632)
			ret, isStraddle := getVerifyOffsetAndLength(fileSize, offset)
			So(isStraddle, ShouldBeTrue)
			So(ret[0] == 688, ShouldBeTrue)
			So(ret[1] == 12, ShouldBeTrue)
			So(ret[2] == 708, ShouldBeTrue)
			So(ret[3] == 0, ShouldBeTrue)
		})

		Convey("Handle non-straddle case", func() {
			stubs := Stub(&ipfsVerifyReadLength, int64(10))
			stubs.Stub(&IpfsChunkSize, int64(100))
			defer stubs.Reset()
			fileSize := int64(1000)
			offset := int64(632)
			ret, isStraddle := getVerifyOffsetAndLength(fileSize, offset)
			So(isStraddle, ShouldBeFalse)
			So(ret[0] == 688, ShouldBeTrue)
			So(ret[1] == 10, ShouldBeTrue)
			So(ret[2] == -1, ShouldBeTrue)
			So(ret[3] == -1, ShouldBeTrue)
		})

		Convey("Handle read till file end", func() {
			stubs := Stub(&ipfsVerifyReadLength, int64(10))
			stubs.Stub(&IpfsChunkSize, int64(100))
			defer stubs.Reset()
			fileSize := int64(1000)
			offset := int64(995)
			ret, isStraddle := getVerifyOffsetAndLength(fileSize, offset)
			So(isStraddle, ShouldBeFalse)
			So(ret[0] == 1083, ShouldBeTrue)
			So(ret[1] == 5, ShouldBeTrue)
			So(ret[2] == -1, ShouldBeTrue)
			So(ret[3] == -1, ShouldBeTrue)
		})

		Convey("Handle case when file size = 0", func() {
			stubs := Stub(&ipfsVerifyReadLength, int64(10))
			stubs.Stub(&IpfsChunkSize, int64(100))
			defer stubs.Reset()
			fileSize := int64(0)
			offset := int64(0)
			ret, isStraddle := getVerifyOffsetAndLength(fileSize, offset)
			So(isStraddle, ShouldBeFalse)
			So(ret[0] == 0, ShouldBeTrue)
			So(ret[1] == 0, ShouldBeTrue)
			So(ret[2] == -1, ShouldBeTrue)
			So(ret[3] == -1, ShouldBeTrue)
		})

		Convey("Handle case when offset = 0", func() {
			stubs := Stub(&ipfsVerifyReadLength, int64(10))
			stubs.Stub(&IpfsChunkSize, int64(100))
			defer stubs.Reset()
			fileSize := int64(1000)
			offset := int64(0)
			ret, isStraddle := getVerifyOffsetAndLength(fileSize, offset)
			So(isStraddle, ShouldBeFalse)
			So(ret[0] == 8, ShouldBeTrue)
			So(ret[1] == 10, ShouldBeTrue)
			So(ret[2] == -1, ShouldBeTrue)
			So(ret[3] == -1, ShouldBeTrue)
		})

		Convey("Handle case when offset > fileSize", func() {
			stubs := Stub(&ipfsVerifyReadLength, int64(10))
			stubs.Stub(&IpfsChunkSize, int64(100))
			defer stubs.Reset()
			fileSize := int64(1000)
			offset := int64(1632)
			ret, isStraddle := getVerifyOffsetAndLength(fileSize, offset)
			So(isStraddle, ShouldBeFalse)
			So(ret[0] == 688, ShouldBeTrue)
			So(ret[1] == 10, ShouldBeTrue)
			So(ret[2] == -1, ShouldBeTrue)
			So(ret[3] == -1, ShouldBeTrue)
		})

		Convey("Handle case when offset > fileSize and straddle", func() {
			stubs := Stub(&ipfsVerifyReadLength, int64(15))
			stubs.Stub(&IpfsChunkSize, int64(100))
			defer stubs.Reset()
			fileSize := int64(1000)
			offset := int64(1632)
			ret, isStraddle := getVerifyOffsetAndLength(fileSize, offset)
			So(isStraddle, ShouldBeTrue)
			So(ret[0] == 688, ShouldBeTrue)
			So(ret[1] == 12, ShouldBeTrue)
			So(ret[2] == 708, ShouldBeTrue)
			So(ret[3] == 3, ShouldBeTrue)
		})

		Convey("Handle case when offset > fileSize and read till file end", func() {
			stubs := Stub(&ipfsVerifyReadLength, int64(15))
			stubs.Stub(&IpfsChunkSize, int64(100))
			defer stubs.Reset()
			fileSize := int64(1000)
			offset := int64(1995)
			ret, isStraddle := getVerifyOffsetAndLength(fileSize, offset)
			So(isStraddle, ShouldBeFalse)
			So(ret[0] == 1083, ShouldBeTrue)
			So(ret[1] == 5, ShouldBeTrue)
			So(ret[2] == -1, ShouldBeTrue)
			So(ret[3] == -1, ShouldBeTrue)
		})
	})
}
