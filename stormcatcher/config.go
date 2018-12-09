package main

// will be set in flags
var listenAddressAndPort string
var redisHostPort string
var ipfsHostPort string
var queueConcurrency = 10
var ipfsGCInterval = 100                         // in seconds
var ipfsUnpinInterval = 100                      // in seconds
var unpinInterval = 60                           // in seconds
var RestoredFileUnpinInterval = int64(3600 * 24) // in seconds
