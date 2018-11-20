## FileStorm - 在墨客子链上实现的IPFS存储平台

区块链的一个核心价值就是建立了一套信任机制，让不同的个体和团体能把计算资源共享，将计算能力大大提升。同理，区块链也可以用来实现数据存储的共享，提升全人类数据的存储和分享能力。

IPFS是一个去中心化多节点存储的重要协议。这个协议的目标是取代传统的互联网协议HTTP，让我们的互联网速度更快，更安全，更开放。但IPFS还需要通过基于区块链实现的奖励机制，来鼓励节点参与IPFS的网络存储，提供服务并获取回报。但是IPFS只是一个协议，它还不足以建立一个分布式存储平台。在这个协议之上，还缺少一个文件存储的奖励机制。有了这个机制，有存储需求的用户使用代币，提供存储空间的矿工通过竞争来存储文件获取代币。这样大家才会愿意把富余的硬盘拿出来存储别人的文件，才能搭建一个可商用的全球化的存储空间，将来IPFS才有可能取代HTTP协议，打造一个全新的互联网。

所以，基于IPFS协议搭建的存储平台需要做下面这些事情：

1. 确保文件被复制到多个节点上。
1. 查找并修复缺损文件。
1. 文件存储提供方可以得到奖励。
1. 文件存储可以被证明。证明分两块：
    1. 复制证明（proof-of-replication ）- 存储提供节点必须能证明文件确实被存储在了它的物理硬盘上。
    2. 时空证明（proof-of-spacetime）- 存储提供节点必须能证明文件在一个确定的时间里被存储了。

墨客是一个为去中心化应用而生的多层次区块链，每一个子链都将会用来支持一个应用，而大部分的应用都有存储需求，这是区块链应用不可或缺的一部分。FileStorm就是这样一条子链。用户可以通过调用智能合约把文件写入和读出IPFS网络。FileStorm子链定期做文件验证确保文件都被存储，然后给存储节点支付收益。因为底层协议一致，通过FileStorm存储的文件跟IPFS主网是相连的，所以实现了IPFS存储的墨客，将成为一个完美的去中心化应用开发平台。

而基于IPFS协议搭建的存储平台需要的所有条件，都可以通过墨客子链来轻松实现：

1. 建立一条由多个可以提供存储功能的节点组成的子链。
1. 通过子链自带的节点更新，备份，和刷新功能实现文件修复。
1. 子链节点通过出块得到奖励。
1. FileStorm共识机制从时间和空间上证明了文件被复制。

### FileStorm共识

FileStorm共识和ProcBlizzard共识类似。子链节点按顺序轮流出块，每n秒钟一个块，n可设置，现在设置为10。FileStorm的不同之处在于，每个FileStorm子链的区块头上多了两个参数，一个是随机数，一个是哈希值。这两个参数是用来做文件验证的。证明每个节点上都存了该存的文件。在第一版的FileStorm子链上，所有的文件在每个节点上都必须存一份。每m个区块进行一次文件验证。没可设置，现在设置为10。验证的方法是，出块节点拿到上一个区块上的随机数，然后通过这个随机数进行计算，找到它在自己的IPFS中对应的文件，并找到文件所有子节中对应的起始位置，然后往后面拿256个字节（字节数不够就拿到文件最后一个字节），做一个哈希。然后把哈希值写到区块头上。同时生成一个新的随机数写到区块头上，给下一个区块用。其他节点收到新区块后，也要用同样的方法对本地IPFS节点里的对应文件进行验证，验证结果一致，就将区块写进本地区块链上。如果不一致，就等待下一个区块。这样，FileStorm共识就确保子链上每个节点都保存了该保存的文件。

### FileStorm 子链节点

运行FileStorm子链需要安装下面四个模块

1. SCSServer - 墨客子链节点程序。
1. redis - 本地数据库，用于存储文件公共哈希和私密哈希的对应。
1. IPFS Monkey - 文件管理助手，用于文件从IPFS Daemon里的读写删除和验证
1. IPFS Daemon - 文件以IPFS的方式存储的主要平台。

IPFS Daemon由IPFS源代码生成，没有改动。所以FileStorm子链是一种开放式架构，可以和其他基于IPFS的存储设备兼容。

### FileStorm子链节点的安全

为了确保墨客子链节点的安全性，子链节点是不暴露IP的，所有的信息传递都是通过VNODE。但是，IPFS能够根据文件哈希值定位具体节点的ip地址。所以，我们会把文件的哈希加密。但是，如果是公开文件，子链上会确保有一个节点把文件用原始哈希来存储。

### FileStorm 用户指南

FileStorm的使用可以看这里。[FileStorm用户指南](FileStormUserGuide.md)

### IPFS合约

IPFS合约必须提供四个基本函数：
function write(string fileHash, bool publicFile)
调用这个函数可以把本地文件写到FileStorm子链节点上。如果输入的publicFile参数是true，文件将是公开的，通过同样的fileHash也可以从子链上读出来。如果输入的publicFile参数是false，文件将是不公开公开的，用fileHash不可以从子链上读出来。只能调用合约的读文件函数。

function read(string fileHash)
调用这个函数可以把FileStorm子链节点上的文件读到本地IPFS Daemon中来。

function remove(string fileHash)
调用这个函数可以把文件从FileStorm子链上删除。

function verify(string fileHash)
调用这个函数可以验证节点上文件是否存在。

```

pragma solidity ^0.4.18;

// ----------------------------------------------------------------------------
// Moac FileStorm MicroChain Contract Standard Interface
// https://github.com/MOACChain/moac-core/wiki/FileStorm
// ----------------------------------------------------------------------------
contract FileStormInterface {
    function list() public returns (uint count);
    function read(string fileHash) public;
    function write(string fileHash) public;
    function remove(string fileHash) public;
}

// ----------------------------------------------------------------------------
// Precompiled contract executed by Moac MicroChain SCS Virtual Machine
// ----------------------------------------------------------------------------
contract Precompiled10 {
  function ipfsFile (string, uint) public;
}

// ----------------------------------------------------------------------------
// FileStormOwners MicroChain contract providing list/read/write/remove ipfs files
// ----------------------------------------------------------------------------
contract FileStormOwnersMicroChain is FileStormInterface {
    
    enum AccessType { read, write, remove, verify }

    struct File {
        string fileHash;
        uint fileSize;
    }

    mapping (address=>File[]) public fileMap;
    string[] public fileHashes;

    Precompiled10 constant PREC10 = Precompiled10(0xA); 
    
    function list() public returns (uint count) {
        return fileHashes.length;
    }
    
    function fileCount() public view returns (uint count) {
        return fileMap[msg.sender].length;
    }

    
    function write(string fileHash) public {
        for (uint i=fileHashes.length; i>0; i--){
            if (compareStringsbyBytes(fileHash, fileHashes[i-1])){
                return;
            }
        }
        fileHashes.push(fileHash);
        
        File memory aFile;
        aFile.fileHash = fileHash;
        aFile.fileSize = 0;
        fileMap[msg.sender].push(aFile);

        PREC10.ipfsFile(fileHash, uint(AccessType.write));
    }
    
    function read(string fileHash) public {
        PREC10.ipfsFile(fileHash, uint(AccessType.read));
    }
    
    function remove(string fileHash) public {

        bool removable = false;
        for (uint i=fileMap[msg.sender].length; i>0; i--){
            if (compareStringsbyBytes(fileHash, fileMap[msg.sender][i-1].fileHash)){
                for (uint j=i-1; j<fileMap[msg.sender].length-1; j++) {
                    fileMap[msg.sender][j]=fileMap[msg.sender][j+1];
                }
                delete fileMap[msg.sender][fileMap[msg.sender].length-1];
                fileMap[msg.sender].length--;
                removable = true;
            }
        }
        
        if (removable) {
            for (i=fileHashes.length; i>0; i--){
                if (compareStringsbyBytes(fileHash, fileHashes[i-1])){
                    for (j=i-1; j<fileHashes.length-1; j++) {
                        fileHashes[j]=fileHashes[j+1];
                    }
                    delete fileHashes[fileHashes.length-1];
                    fileHashes.length--;
                }
            }
        }
        PREC10.ipfsFile(fileHash, uint(AccessType.remove));
    }
    
    function compareStringsbyBytes(string s1, string s2) private pure returns(bool)
    {
        bytes memory s1bytes = bytes(s1);
        bytes memory s2bytes = bytes(s2);
        if(s1bytes.length!=s2bytes.length) {
            return false;
        }
        else{
            for(uint i = 0;i<s1bytes.length;i++)
            {
                if(s1bytes[i] != s2bytes[i])
                 return false;
            }
            return true;
        }
    }
}
```

FileStorm子链技术于2018年7月14日正式发布。