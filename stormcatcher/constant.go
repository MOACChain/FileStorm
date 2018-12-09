package main

var IpfsWriteQueueName = "ipfs_write_queue"
var IpfsReadQueueName = "ipfs_read_queue"
var IpfsDeleteQueueName = "ipfs_delete_queue"
var IpfsFileHashMappingName = "ipfs_file_hash_mapping"
var IpfsFileHashStatName = "ipfs_file_hash_stat"
var IpfsProxyReadQueueName = "ipfs_proxy_read_queue"
var IpfsProxyWriteQueueName = "ipfs_proxy_write_queue"
var IpfsUnpinFileHashQueueName = "ipfs_unpin_filehash_queue"
var IpfsPrefix = "ipfs_tmp_"
var IpfsChunkSize = int64(16 * 1024)  // in bytes
var ipfsVerifyReadLength = int64(256) // in bytes
