## FileStorm User Guide

FileStorm is a decentralized storage platform implemented with IPFS and MOAC. The files are stored on millions of data nodes provided by people around the world in IPFS format in exchange for MOAC. FileStorm is a project that involves three types of people.

1. Storage Provider
Storage providers provides the hardware devices used for storage, such as computers with large volume of storage, or customized storage boxes. FileStorm program needs to be installed on these devices so they can connect to Moac FileStorm microchains as well as IPFS network. Storage providers can gain reward in MOAC by offering the storage service.

1. Dapp Developers
Dapp Developers are the creator of FileStorm microchains. They can create a dedicated microchain used for storage only for their own Dapp. Moac Foundation will also create a public FileStorm microchain. Dapp Developers will have to pay MOAC to deploy and maintain a FileStorm. 

1. Storage End Users
Storage end users will use FileStorm through Dapps. The end users do not need MOAC to use FileStorm, but they may have to pay to use the Dapps.

Our user guide has three parts for the three type of users.

### Storage Providers

Running FileStorm Subchain needs the following 4 modules:

* SCSServer - Moac subchain node program.
* redis - Local database to store public and private file hash
* IPFS Daemon - IPFS storage server program。
* IFPS Monkey - Used by Moac subchain node program to access IPFS

We can download all these modules one by ore, or install them by using Docker. Let's start with installing them one by one.

redis：

#### Ubuntu
```
sudo apt update
sudo apt full-upgrade
sudo apt install build-essential tcl
curl -O http://download.redis.io/redis-stable.tar.gz
tar xzvf redis-stable.tar.gz
sudo apt install redis-server
```
#### CentOs
```
sudo yum install epel-release
sudo yum update
sudo yum install redis
```

ipfs
可以从这个链接[下载](https://dist.ipfs.io/#go-ipfs)最新版本ipfs软件包。我们以ubuntu版本为例
```
wget https://dist.ipfs.io/go-ipfs/v0.4.17/go-ipfs_v0.4.17_linux-amd64.tar.gz
tar xvfz go-ipfs_v0.4.17_linux-amd64.tar.gz
sudo mv go-ipfs/ipfs /usr/local/bin/ipfs
```

scsserver 可以从这个[链接](https://github.com/MOACChain/moac-core/releases/tag/v0.9)下载最新版本。我们以ubuntu版本为例。

也可以从这里下载Docker版本。关于Docker的安装和使用，可以看着里：[Get Started with Docker](https://docs.docker.com/get-started/).

#### 设置

设置文件是../config/userconfig.json

VnodeServiceCfg的设置是从 [Node info - Testnet](https://nodes101.moac.io/)上，找到Vnode Protocol Pool下面的任何一个Vnode/Port组合加上。
Beneficiary设置成SCS节点收益的受益人钱包地址。


服务器上必须把下面的端口打开:
* 4001
* 5001
* 8080

#### 运行

第一次使用必须先生成IPFS文件库

```
ipfs init
````

然后就可以运行FileStorm了。FileStorm可以调用下面的脚本运行。

```
cd scsserver
./run_filestorm_scs.sh
```

脚本代码如下，在后台调用四个模块
```
#!/bin/bash  
echo "Starting FileStorm SCS--"

nohup ipfs daemon > ipfs.out 2>&1 &
echo "IPFS Daemon started."

nohup redis-server > ipfs.out 2>&1 &
echo "Redis Server started. "

nohup ./ipfs_monkey > ipfs.out 2>&1 &
echo "IPFS Monkey started."

nohup ./scsserver > scs.out 2>&1 &
echo "SCS started."
```

可以用调用下面的脚本停止。

`./stop_filestorm_scs.sh`

脚本代码如下，关闭四个模块
```
#!/bin/bash  
echo "Stoping FileStorm SCS--"
pkill ipfs_monkey
pkill redis-server
pkill ipfs
pkill scsserver
echo "FileStorm SCS Stopped."
```

*为了让子链节点正常使用，我们还需要给每个子链节点地址打0.5个Moac。*

#### 监测

可以用下面的指令检测程序运行进程

`tail -f scs.out`

### 应用部署方

1. 本地必须开一个vnode连接到Moac testnet上。在
https://github.com/MOACChain/moac-core/releases
下载Nuwa1.0.2
1. 用下面的指令开启vnode `
./moac --testnet --rpc --rpccorsdomain "http://wallet.moac.io" console`
1. 打开 http://wallet.moac.io 
1. 发合约 DeploySubChainBase.sol
1. 从 [Node info - Testnet](https://nodes101.moac.io/) 上找SubChainProtocolBase pool地址和 Vnodeproxy pool地址
1. 发子链合约 FileStormMicroChain.sol
1. 注册检测子链
1. 子链浏览器检测

### 存储使用者

存储使用者一般都是通过应用来存储文件。应用部署方则通过部署子链合约 FileStormMicroChain.sol把文件存到FileStorm上，或者读出来。

我们可以用如下的步骤演示文件读写的流程。方便应用方了解熟悉后集成到应用中。

1. 本地必须开一个vnode连接到Moac testnet上。在
https://github.com/MOACChain/moac-core/releases
下载Nuwa1.0.2。（希望将来应用开发者会把这个模块）
1. 用下面的指令开启vnode `
./moac --testnet console`
1. 在本地安装IPFS（希望将来应用开发者会把这个模块集成到应用中。）可以从这个链接[下载](https://dist.ipfs.io/#go-ipfs)最新版本ipfs软件包。我们以ubuntu版本为例
```
wget https://dist.ipfs.io/go-ipfs/v0.4.17/go-ipfs_v0.4.17_linux-amd64.tar.gz
tar xvfz go-ipfs_v0.4.17_linux-amd64.tar.gz
sudo mv go-ipfs/ipfs /usr/local/bin/ipfs
```
4. 我们本地生成一个测试文件。`vi newtestfile.txt`
4. 我们将测试文件放到IPFS中：`ifps add newtestfile.txt`
4. 我们将拿到的文件hash生成16进制代码。可以在这个网站实现：https://codebeautify.org/string-hex-converter。
也可以用下面这段代码得到。
```
npm install --save ethereumjs-abi

var abi = require('ethereumjs-abi');
var original = 'QmQNe96LqV5TcRQyBz12iQXPZQjemBqkgnpHki3wmKjtd6';
var encoded = abi.simpleEncode('write(string)', original);

console.log('original', original); 
console.log('encoded', encoded.toString('hex'));
```

7. 得到的字节数是46位的16进制数（因为每一位两个数字，一共92个数字）。因为solidty参数的存储空间是32位，46位的16进制数需要两个存储空间才行，然后我们要把得到的16进制数后面补上足够多的0，变成一个64位的16进制数（一共108个数字）。
8. 调用函数处理文件有如下三个函数，分别可以对文件进行写，读，删。
8. from: 这必须是本机keystore里存在的一个账号。如果是vnode里第一次生成的就是chain3.mc.accounts[0]，必须先进行一下`personal.unlockAccount(mc.accounts[0])`解锁使用。
8. to: subchainbaseaddress是SubChainBase合约地址，必须由应用项目方提供，我们可以用前面测试得到的地址。
8. data: 把第7步得到的数字加到data的数值里2e的后面。
8. 每次调用要把nonce手动加1。（下一个版本会有更好的方法拿到nonce）
8. via必须跟moac同文件夹下的vnodeproxy.json文件里
    

```
// write(fileHash)
chain3.mc.sendTransaction(
{
  from: chain3.mc.accounts[0],
  value:chain3.toSha('0','mc'),
  to: subchainbaseaddress,
  gas: "200000",
  gasPrice: chain3.mc.gasPrice,
  shardingflag: 1,
  data: '0xba3835ba00000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002e'
  nonce: 1,
  via: chain3.mc.accounts[0]
});

// read(fileHash)
chain3.mc.sendTransaction(
{
  from: mc.accounts[0],
  value:chain3.toSha('0','mc'),
  to: subchainbaseaddress,
  gas: "200000",
  gasPrice: chain3.mc.gasPrice,
  shardingflag: 1,
  data: '0x616ffe830000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000002e'
  nonce: 2,
  via: mc.accounts[0]
});

// remove(fileHash)
chain3.mc.sendTransaction(
{
  from: mc.accounts[0],
  value:chain3.toSha('0','mc'),
  to: subchainbaseaddress,
  gas: "200000",
  gasPrice: chain3.mc.gasPrice,
  shardingflag: 1,
  data: '0x80599e4b0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000002e'
  nonce: 3,
  via: mc.accounts[0]
});
```

调用结果：

Write：IPFS文件被存到FileStorm子链的每一个节点上，文件Hash值被改变。
Read：FileStorm子链的每一个节点上都会出现原始Hash值的原文件。（会在24小时后自动删除。）
Remove：IPFS文件会从FileStorm子链的每一个节点上被删除。