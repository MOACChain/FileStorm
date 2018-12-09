package main

import (
	"encoding/json"
	"errors"
	"strconv"

	"github.com/go-redis/redis"
)

var redisClient *redis.Client

func initRedisClient() {
	redisClient = getRedisClient(redisHostPort, "")
}

func getRedisClient(redisHostPort string, redisPassword string) *redis.Client {
	client := redis.NewClient(&redis.Options{
		Addr:     redisHostPort,
		Password: redisPassword, // no password set
		DB:       0,             // use default DB
	})
	return client
}

var AddTaskToQueue = func(queueName string, taskName string) error {
	if result, err := redisClient.LPush(queueName, taskName).Result(); err != nil {
		log.Info("Redis LPush failed", result, err)
		return err
	} else {
		log.Info("Redis LPush", queueName, taskName)
		return nil
	}
}

func getTaskFromQueueBlock(queueName string) (string, error) {
	r, err := redisClient.BRPop(0, queueName).Result()
	// r: key, value
	if err != nil {
		return "", err
	}
	return r[1], nil
}

var GetAlteredFileHash = func(originalFileHash string) string {
	s, _ := redisClient.HGet(IpfsFileHashMappingName, originalFileHash).Result()
	log.Info(IpfsFileHashMappingName, originalFileHash, s)
	return s
}

func updateFileHashMapping(originalFileHash string, alteredFileHash string) error {
	// make the mapping bi-directional
	redisClient.HSet(IpfsFileHashMappingName, originalFileHash, alteredFileHash)
	redisClient.HSet(IpfsFileHashMappingName, alteredFileHash, originalFileHash)
	log.Info(IpfsFileHashMappingName, originalFileHash, alteredFileHash)

	return nil
}

func updateFileHashStat(originalFileHash string, stat FileHashStat) error {
	mStat, _ := json.Marshal(stat)
	redisClient.HSet(IpfsFileHashStatName, originalFileHash, string(mStat))
	log.Info(IpfsFileHashStatName, originalFileHash, string(mStat))
	return nil
}

func deleteFileHashStat(originalFileHash string) error {
	_, err := redisClient.HDel(IpfsFileHashStatName, originalFileHash).Result()
	if err != nil {
		return err
	} else {
		return nil
	}
}

func deleteFileHashMapping(h string, tableName string) error {
	delete, err := redisClient.HDel(tableName, h).Result()
	if delete == 1 {
		log.Info("Deleted redis entry", h, "from", tableName)
	} else {
		log.Info("Deleted no redis entry with", h, "from", tableName)
	}
	if err != nil {
		return err
	} else {
		return nil
	}
}
func deleteFileHashMappings(originalFileHash string) error {
	alteredFileHash := GetAlteredFileHash(originalFileHash)
	var retErr error = nil
	if err := deleteFileHashMapping(originalFileHash, IpfsFileHashMappingName); err != nil {
		retErr = err
	}
	if err := deleteFileHashMapping(alteredFileHash, IpfsFileHashMappingName); err != nil {
		retErr = err
	}

	return retErr
}

type FileHashStat struct {
	Size int64 `json:"size"`
}

func getFileHashStat(originalFileHash string) (error, *FileHashStat) {
	result, _ := redisClient.HGet(IpfsFileHashStatName, originalFileHash).Result()
	if result == "" {
		return errors.New("File not found"), nil
	}
	stat := new(FileHashStat)
	json.Unmarshal([]byte(result), &stat)

	return nil, stat
}

func unpinFileHashQueueAdd(unpinFileHash string, unpinTimeStamp int64) error {
	// if called with existing filehash, its unpinTimeStamp will be updated.
	value := redis.Z{
		Member: unpinFileHash,
		Score:  float64(unpinTimeStamp),
	}
	_, err := redisClient.ZAdd(IpfsUnpinFileHashQueueName, value).Result()
	return err
}

func unpinFileHashQueueRemove(unpinFileHash string) error {
	_, err := redisClient.ZRem(IpfsUnpinFileHashQueueName, unpinFileHash).Result()
	if err != nil {
		log.Info("Can not remove file hash from unpin queue", unpinFileHash)
	} else {
		log.Info("Removed file hash from unpin queue", unpinFileHash)
	}
	return err
}

func unpinFileHashQueueRange(cutoffTimeMin int64, cutoffTimeMax int64) ([]string, error) {
	zRange := redis.ZRangeBy{
		Min: strconv.Itoa(int(cutoffTimeMin)),
		Max: strconv.Itoa(int(cutoffTimeMax)),
	}
	return redisClient.ZRangeByScore(IpfsUnpinFileHashQueueName, zRange).Result()
}
