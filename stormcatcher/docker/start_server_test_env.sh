#!/bin/bash
/etc/init.d/redis-server start
ipfs init
ipfs daemon &
echo "Sleep for 5 seonds"
sleep 5
cd /ipfs_monkey && go test -v
