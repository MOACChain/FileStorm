package main

import (
	"testing"
	"time"

	. "github.com/smartystreets/goconvey/convey"
)

func unpinFileHashQueueGet(unpinFileHash string) (float64, error) {
	return redisClient.ZScore(IpfsUnpinFileHashQueueName, unpinFileHash).Result()
}

func TestUnpinFileHashQueue(t *testing.T) {
	Convey("Test unpin filehash queue operations", t, func() {
		// setup
		initRedisClient()

		Convey("Test unpin filehash queue add", func() {
			// set the first member
			t := time.Now().Unix()
			unpinFileHashQueueAdd("some_hash", t)
			_t, _ := unpinFileHashQueueGet("some_hash")
			So(int64(_t) == t, ShouldBeTrue)
			queueSize, _ := redisClient.ZCard(IpfsUnpinFileHashQueueName).Result()

			// update the member from previous step, should create no new member
			t2 := time.Now().Unix()
			unpinFileHashQueueAdd("some_hash", t2)
			_t2, _ := unpinFileHashQueueGet("some_hash")
			So(int64(_t2) == t2, ShouldBeTrue)
			_queueSize, _ := redisClient.ZCard(IpfsUnpinFileHashQueueName).Result()
			// queue size should not change since this is a update to the same key
			So(_queueSize == queueSize, ShouldBeTrue)
		})

		Convey("Test unpin filehash queue range query", func() {
			// set the first member
			time.Sleep(time.Duration(2) * time.Second)
			t := time.Now().Unix()
			unpinFileHashQueueAdd("some_hash_1", t+1)
			unpinFileHashQueueAdd("some_hash_2", t+2)
			unpinFileHashQueueAdd("some_hash_3", t+3)
			unpinFileHashQueueAdd("some_hash_4", t+4)
			unpinFileHashQueueAdd("some_hash_5", t+5)

			members, _ := unpinFileHashQueueRange(t, t+2)
			So(len(members) == 2, ShouldBeTrue)
			So(members[0] == "some_hash_1", ShouldBeTrue)
			So(members[1] == "some_hash_2", ShouldBeTrue)

			emptyMembers, _ := unpinFileHashQueueRange(t+6, t+7)
			So(len(emptyMembers) == 0, ShouldBeTrue)
		})
	})
}
