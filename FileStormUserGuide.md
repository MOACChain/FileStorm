## FileStorm 用户指南

FileStorm是在墨客子链上实现的基于IPFS的去中心化存储平台。详细介绍可参看这篇[FileStorm - 在墨客子链上实现的IPFS存储平台](README.md)。这个平台的用户有三类。

1. 存储提供方：
存储提供方将会提供用于存储的硬件设备，如带大容量硬盘的电脑，或者订制的硬件盒子。设备需要安装FileStorm程序，用来链接墨客FileStorm子链，和IPFS网络。为FileStorm提供存储可以得到收益，就是俗称的存储挖矿。

1. 应用部署方：应用部署方为FileStorm子链的创建者。他们可以搭建一个专门的子链为自己的应用做存储。应用部署方需要承担运营FileStorm子链的费用（墨客也会做为应用部署方部署一条FileStorm子链供用户使用。）

1. 存储使用者：存储的使用者通过使用应用部署方部署的应用来存储和读取文件。存储使用者无需为使用FileStorm付费。但是可能要承担使用应用的费用。

我们的用户指南将根据用户的不同，，分成三部分。

### 存储提供方

运行FileStorm子链需要安装下面四个模块

* redis - 本地数据库，用于缓存文件调用的请求。
* IPFS Daemon - 文件以IPFS的方式存储的主要平台。
* SCSServer - 墨客子链节点程序。
* stormcatcher - 墨客子链节点对IPFS的调用。

这些模块可以用以下的方式一个一个下载安装，也可以用Docker的方式安装。先介绍一个一个下载安装流程。

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
可以用下面的方法安装与filestorm的ipfs软件包。

#### Ubuntu
```
curl https://dist.ipfs.io/go-ipfs/v0.4.18/go-ipfs_v0.4.18_linux-amd64.tar.gz > go-ipfs.tar.gz
tar xvfz go-ipfs.tar.gz
sudo mv go-ipfs/ipfs /usr/local/bin/ipfs
```
#### CentOs
```
curl https://dist.ipfs.io/go-ipfs/v0.4.17/go-ipfs_v0.4.17_linux-amd64.tar.gz > go-ipfs.tar.gz
tar xvfz go-ipfs.tar.gz
sudo mv go-ipfs/ipfs /usr/local/bin/ipfs
```

scsserver 和 stormcatcher 可以从这个[链接](https://github.com/MOACChain/FileStorm/tree/master/release)下载最新版本。

下载后，下载的文件包会在服务器端生成filestorm工作文件夹。将文件夹内解压出如下文件：
* scsserver
* stormcatcher
* userconfig.json
* run_filestorm_scs.sh
* stop_filestorm_scs.sh

也可以从这里下载Docker版本。关于Docker的安装和使用，可以看着里：[Get Started with Docker](https://docs.docker.com/get-started/).

#### 设置

设置文件是userconfig.json

VnodeServiceCfg的设置是从 [Node info - Testnet](https://nodes101.moac.io/)上，找到Vnode Protocol Pool下面的任何一个Vnode/Port组合加上。
Beneficiary设置成SCS节点收益的受益人钱包地址。

服务器上必须把下面的端口打开:
* 4001
* 5001
* 8080

如果节点做为子链监控节点，还需要打开run_filestorm_scs.sh中定义的rpc端口，缺省是50068.

#### 运行

第一次使用必须先生成IPFS文件库

```
ipfs init
````

然后还需要生成一个节点ID。用如下指令
```
cd filestorm
./scsserver
```
生成的ID可以通过`ll scskeystore`看到。ID就是一个地址，是scskeystore下的文件名-后面的哪一部分在前面加上0x。
如文件名是UTC--2018-09-23T05-52-26.554142261Z--e09f56c0c8c528b14ea594764c09c7ede73f88c0
ID就是0xe09f56c0c8c528b14ea594764c09c7ede73f88c0

我们必须往这个ID里打一定的MOAC，0.05个即可，让这个ID记录到墨客区块链的主链上去。（可以用任何墨客钱包，如wallet.moac.io来做。）


然后就可以运行FileStorm了。FileStorm可以调用下面的脚本运行。

```
./run_filestorm_scs.sh
```

脚本代码如下，在后台调用四个模块
```
#!/bin/bash
echo "Starting FileStorm SCS--"

#IPFS_PATH=~/.ipfs 
nohup ipfs daemon > ipfs.out 2>&1 &
echo "IPFS Daemon started."

nohup redis-server --port 6373 > ipfs.out 2>&1 &
echo "Redis Server started. "

nohup ./stormcatcher-linux-amd64 --listen-host-port 127.0.0.1:18083 --redis-host-port 127.0.0.1:6373 --ipfs-host-port 127.0.0.1:5001 --subChain-contract-address 0x13512e62c8Cb1De316F7C442d767d0365350720c --subChain-address 127.0.0.1:50068/rpc > ipfs.out 2>&1 &
echo "Storm Catcher started."

nohup ./scsserver-linux-amd64 --rpc --rpcaddr 127.0.0.1 --rpcport 50068 --verbosity 4 > scs.out 2>&1 &
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

可以用下面的指令检测墨客子链运行进程

`tail -f scs.out`

可以用下面的指令检测IPFS成勋运行进程

`tail -f scs.out`

### 应用部署方

1. 本地必须开一个vnode连接到Moac testnet上。在
https://github.com/MOACChain/moac-core/releases
下载Nuwa1.0.7
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

7. 得到的字节数是46位的16进制数（因为每一位两个数字，一共92个数字）。因为solidty参数的存储空间是32位，46位的16进制数需要两个存储空间才行，然后我们要把得到的16进制数后面补上足够多的0，变成一个64位的16进制数（一共128个数字）。
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