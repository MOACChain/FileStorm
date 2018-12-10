# This Makefile is meant to be used by people that do not usually work
# with Go source code. If you know what GOPATH is then you probably
# don't need to bother with make.

GOBIN = $(shell pwd)/build/bin
GO ?= latest

all:
	build/env.sh go run build/mci.go install

# For FileStorm Project
storm:
	go build -o build/bin/stormcatcher storm_catcher.go logging.go constant.go config.go ipfs.go redis.go handler.go

storm_test_local:
	go test -v handler_test.go constant.go handler.go logging.go storm_catcher.go config.go ipfs.go redis.go \
	storm_catcher_test.go integration_test.go ipfs_test.go

storm_test_docker: storm_docker_test_env
	docker run -it -e "TERM=xterm-256color" heavenstar/moac:ipfs_test_env

storm_docker_prod_env:
	docker build -t moacofficial/moac:ipfs -f addins/ipfs/docker/dockerfile_prod_env .

storm_docker_test_env:
	docker build -t heavenstar/moac:ipfs_test_env -f addins/ipfs/docker/dockerfile_test_env .


storm-linux-amd64:
	go run build/mci.go xgo -- --go=$(GO) --targets=linux/amd64 -v .
	@echo "Linux amd64 cross compilation done:"
	@ls -ld $(GOBIN)/stormcatcher-linux-* | grep amd64

storm-windows-amd64:
	go run build/mci.go xgo -- --go=$(GO) --targets=windows/amd64 -v .
	@echo "Windows amd64 cross compilation done:"
	@ls -ld $(GOBIN)/stormcatcher-windows-* | grep amd64

storm-linux-arm64:
	go run build/mci.go xgo -- --go=$(GO) --targets=linux/arm64 -v .
	@echo "Linux ARM64 cross compilation done:"
	@ls -ld $(GOBIN)/stormcatcher-linux-* | grep arm64

storm-darwin-amd64:
	go run build/mci.go xgo -- --go=$(GO) --targets=darwin/amd64 -v .
	@echo "Darwin amd64 cross compilation done:"
	@ls -ld $(GOBIN)/stormcatcher-darwin-* | grep amd64



# The devtools target installs tools required for 'go generate'.
# You need to put $GOBIN (or $GOPATH/bin) in your PATH to use 'go generate'.

devtools:
	env GOBIN= go get -u golang.org/x/tools/cmd/stringer
	env GOBIN= go get -u github.com/jteeuwen/go-bindata/go-bindata
	env GOBIN= go get -u github.com/fjl/gencodec
	env GOBIN= go install ./cmd/abigen

