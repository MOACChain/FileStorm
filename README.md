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

FileStorm共识是在ProcWind共识之上实现的一个dPOS共识。DPOS，Delegated proof of stake，委托权益共识需要选出一组矿工节点来做区块的生产和调度。这些权益所有者需要抵押通证。任何节点如果不正常出块或者不对其他节点的出块做验证，抵押通证将被扣除。这些节点被称之为验证节点，或出块节点。验证节点按顺序轮流出块，每n秒钟一个块，n可设置，现在设置为10。FileStorm的不同之处在于，每个FileStorm子链的区块头上多了两个随机数。这两个随机数是用来做文件验证的。证明每个节点上都存了该存的文件。在FileStorm子链上，节点分成两种：出块节点和存储节点。出块节点负责打包交易，出块，并且产生文件验证随机数。存储节点存储文件，并且对文件进行验证。

出块节点被分到不同的组，每个组是一个存储的基本单位，相当于数据库里的一个Shard(分片)。每个分片里的节点存储空间一样大，存储的文件也是一样。这样一个分片里的多个节点可以互相验证。所有的文件的信息和读写交易都写在区块链上，而文件内容则存在存储节点的IPFS中。

每个存储节点都要定期对本地文件进行验证。初始设定为每40个区块。轮到验证的区块根据两个随机数产生一组文件，然后用第一个随机数找到每个文件中的一个起始位置。从这个起始位置往后数256个字节，得到一组随机的字符串组。然后对这组字符串进行哈希，直到得出一个根哈希值。同时，同分片里的其他节点也要做同样的操作。得到哈希值。被验证节点先将自己的哈希值以交易的方式发到链上。同分片的其他节点收到交易后，跟自己生成的根哈希进行比较，然后将比较结果也以交易的方式发出来。最后进行统计，如果超过半数的节点给出的哈希值跟被验证节点相同。我们认为被验证节点存了所有的文件，我们就会给它奖励。

每个存储节点都会轮流被验证，验证通过得到奖励。文件验证会在每个分片里进行。

用户文件存到链上，会根据一定的算法平均的分配到不同的分片上。

### FileStorm 验证节点

验证节点主要安装下面这个程序来产生和验证区块。并且获取区块奖励。

1. SCSServer - 墨客子链节点程序。

### FileStorm 存储节点

存储节点主要安装下面这些程序来对存储的文件进行验证。验证成功即可获取存储收益。

1. StormCatcher - 文件管理助手，用于对文件读写删除和验证的操作。
1. IPFS Daemon - 文件以IPFS的方式存储的主要平台。
1. redis - 本地数据库，用于缓存文件调用的请求。

IPFS Daemon由IPFS源代码生成，没有改动。所以FileStorm子链是一种开放式架构，可以和其他基于IPFS的存储设备兼容。

### FileStorm Token

FileStorm Token (FST)是发行在墨客区块链上的一个ERC20代币。在FileStorm平台上流通。用户通过支付FST获取平台的使用权，存储提供方提供存储设备得到FST收益。

### FileStorm 用户指南

FileStorm的使用可以看这里。[FileStorm用户指南](FileStormUserGuide.md)

### FileStormChain 合约

FileStormChain合约的源代码如下：
```
pragma solidity ^0.4.24;

// ----------------------------------------------------------------------------
// Precompiled contract executed by Moac MicroChain SCS Virtual Machine
// ----------------------------------------------------------------------------
contract Precompiled10 {
    function ipfsFile(string, uint, uint, string) public;
}

// ----------------------------------------------------------------------------
// Base contract that supports token usage in Dapp.
// ----------------------------------------------------------------------------
contract DappBase {
    struct RedeemMapping {
        address[] userAddr;
        uint[] userAmount;
        uint[] time;
    }

    struct Task {
        bytes32 hash;
        address[] voters;
        bool distDone;
    }

    struct EnterRecords {
        address[] userAddr;
        uint[] amount;
        uint[] time;
        uint[] buyTime;
    }

    RedeemMapping internal redeem;
    address[] public curNodeList;//
    mapping(bytes32 => Task) task;
    mapping(bytes32 => address[]) nodeVoters;
    address internal owner;
    EnterRecords internal enterRecords;
    uint public enterPos;

    function DappBase() public payable {
        owner = msg.sender;
    }

    function getCurNodeList() public view returns (address[] nodeList) {

        return curNodeList;
    }

    function getEnterRecords(address userAddr) public view returns (uint[] enterAmt, uint[] entertime) {
        uint i;
        uint j = 0;

        for (i = 0; i < enterPos; i++) {
            if (enterRecords.userAddr[i] == userAddr) {
                j++;
            }
        }

        uint[] memory amounts = new uint[](j);
        uint[] memory times = new uint[](j);
        j = 0;
        for (i = 0; i < enterPos; i++) {
            if (enterRecords.userAddr[i] == userAddr) {
                amounts[j] = enterRecords.amount[i];
                times[j] = enterRecords.time[i];
                j++;
            }
        }
        return (amounts, times);
    }

    function getRedeemMapping(address userAddr, uint pos) public view returns (address[] redeemingAddr, uint[] redeemingAmt, uint[] redeemingtime) {
        uint j = 0;
        uint k = 0;

        if (userAddr != address(0)) {
            for (k = pos; k < redeem.userAddr.length; k++) {
                if (redeem.userAddr[k] == userAddr) {
                    j++;
                }
            }
        } else {
            j += redeem.userAddr.length - pos;
        }
        address[] memory addrs = new address[](j);
        uint[] memory amounts = new uint[](j);
        uint[] memory times = new uint[](j);
        j = 0;
        for (k = pos; k < redeem.userAddr.length; k++) {
            if (userAddr != address(0)) {
                if (redeem.userAddr[k] == userAddr) {
                    amounts[j] = redeem.userAmount[k];
                    times[j] = redeem.time[k];
                    j++;
                }
            } else {
                addrs[j] = redeem.userAddr[k];
                amounts[j] = redeem.userAmount[k];
                times[j] = redeem.time[k];
                j++;
            }
        }
        return (addrs, amounts, times);
    }

    function redeemFromMicroChain() public payable {
        redeem.userAddr.push(msg.sender);
        redeem.userAmount.push(msg.value);
        redeem.time.push(now);
    }

    function have(address[] addrs, address addr) public view returns (bool) {
        uint i;
        for (i = 0; i < addrs.length; i++) {
            if (addrs[i] == addr) {
                return true;
            }
        }
        return false;
    }

    function updateNodeList(address[] newlist) public {
        //if owner, can directly update
        if (msg.sender == owner) {
            curNodeList = newlist;
        }
        //count votes
        bytes32 hash = sha3(newlist);
        bytes32 oldhash = sha3(curNodeList);
        if (hash == oldhash) return;

        bool res = have(nodeVoters[hash], msg.sender);
        if (!res) {
            nodeVoters[hash].push(msg.sender);
            if (nodeVoters[hash].length > newlist.length / 2) {
                curNodeList = newlist;
            }
        }

        return;
    }

    function postFlush(uint pos, address[] tosend, uint[] amount, uint[] times) public {
        require(have(curNodeList, msg.sender));
        require(tosend.length == amount.length);
        require(pos == enterPos);

        bytes32 hash = sha3(pos, tosend, amount, times);
        if (task[hash].distDone) return;
        if (!have(task[hash].voters, msg.sender)) {
            task[hash].voters.push(msg.sender);
            if (task[hash].voters.length > curNodeList.length / 2) {
                //distribute
                task[hash].distDone = true;
                for (uint i = 0; i < tosend.length; i++) {
                    enterRecords.userAddr.push(tosend[i]);
                    enterRecords.amount.push(amount[i]);
                    enterRecords.time.push(now);
                    enterRecords.buyTime.push(times[i]);
                    tosend[i].transfer(amount[i]);
                }
                enterPos += tosend.length;
            }
        }
    }
}

contract FileStormChain is DappBase {

    enum AccessType {read, write, remove, verify}

    using SafeMath for uint256;

    struct File {
        uint256 fileId;
        string fileHash;
        string fileName;
        uint256 fileSize;
        address fileOwner;
        uint256 createTime;
        uint256 verifiedCount;
    }

    struct Shard {
        uint256 shardId;
        uint nodeCount;
        uint256 weight;
        uint256 size;
        uint256 availableSize; // By byte.
        uint256 percentage; // Use 351234 for 0.351234
    }

    struct Node {
        uint256 shardId;
        address scsId;
        address beneficiary;
        uint256 size;
        uint256 lastVerifiedBlock;
    }

    struct VerifyTransaction {
        address scsId;
        address verifyNodeId;
        uint256 blockNumber;
        string fileHash;
        uint totalCount;
        uint votedCount;
        uint affirmCount;
    }

    uint blockVerificationInterval = 40;
    uint public shardSize = 10;
    uint256 awardAmount = 10000000000000000;  // coin

    address internal owner;
    mapping(address => uint) public admins;

    function FileStormChain() public payable {
        owner = msg.sender;
        capacityMapping[1] = 1024 * 1024 * 1024 * 1024;
        capacityMapping[2] = 1024 * 1024 * 1024 * 1024 * 2;
        capacityMapping[4] = 1024 * 1024 * 1024 * 1024 * 4;
        capacityMapping[8] = 1024 * 1024 * 1024 * 1024 * 8;
        capacityMapping[12] = 1024 * 1024 * 1024 * 1024 * 12;
        capacityMapping[16] = 1024 * 1024 * 1024 * 1024 * 16;
        capacityMapping[32] = 1024 * 1024 * 1024 * 1024 * 32;
    }

    function setCapacity(uint256 weight,uint256 size) public {
        require(msg.sender == owner || admins[msg.sender] == 1);
        capacityMapping[weight] = size;
    }

    function addAdmin(address admin) public {
        require(msg.sender == owner || admins[msg.sender] == 1);
        admins[admin] = 1;
    }

    function removeAdmin(address admin) public {
        require(msg.sender == owner || admins[msg.sender] == 1);
        admins[admin] = 0;
    }

    mapping(uint256 => uint256) public capacityMapping;

    mapping(uint256 => Shard) public shardMapping;
    uint256[] public shardList;
    uint256 public shardCount;
    uint256[] public recentlyUsedList;

    mapping(uint256 => File) public fileMapping;
    uint256[] public fileList;
    uint256 public fileCount;

    mapping(address => Node) public nodeMapping;
    address[] public unassignedNoteList;

    mapping(address => VerifyTransaction[]) public verifyGroupMapping;

    mapping(uint256 => address[]) public shardNodeList;

    mapping(address => uint256[]) private myFileList;
    mapping(uint256 => uint256[]) private shardFileList;
    mapping(uint256 => uint256) private fileShardIdMapping;

    mapping(address => Shard) public nodeShardMapping;

    Precompiled10 constant PREC10 = Precompiled10(0xA);

    // owner functions
    function setBlockVerificationInterval(uint num) public {
        require(msg.sender == owner || admins[msg.sender] == 1);
        blockVerificationInterval = num;
    }

    function setShardSize(uint size) public {
        require(msg.sender == owner || admins[msg.sender] == 1);
        shardSize = size;
    }

    function setAwardAmount(uint256 amount) public {
        require(msg.sender == owner || admins[msg.sender] == 1);
        awardAmount = amount;
    }

    function addShard(uint256 weight) public returns (uint256 shardId) {

        require(msg.sender == owner || admins[msg.sender] == 1);
        require(capacityMapping[weight]>0);
        shardId = shardList.length + 1;
        shardMapping[shardId].shardId = shardId;
        shardMapping[shardId].nodeCount = shardSize;
        shardMapping[shardId].weight = weight;
        shardMapping[shardId].size = capacityMapping[weight];
        shardMapping[shardId].availableSize = capacityMapping[weight];
        shardMapping[shardId].percentage = 0;
        shardList.push(shardId);

        // TO DO: select unassigned nodes and assign shard to them.
        // add all nodes to nodeShardMapping

//		if(shardList.length >1){
//			sortShardList(0, shardList.length-1);
//		}
        shardCount = shardList.length;
        return shardId;
    }

//    function removeShard(uint256 shardId) public returns (bool) {
//        require(msg.sender == owner || admins[msg.sender] == 1);
//        delete shardMapping[shardId];
//        return true;
//    }
    address[] nodeListTemp;
    uint256[] nodeListIndex;

    function addNode(address scsId, address beneficiary, uint256 weight) public returns (uint256)
    {
        require(nodeMapping[scsId].scsId == 0);
        require(capacityMapping[weight]>0);
        nodeMapping[scsId].scsId = scsId;
        nodeMapping[scsId].beneficiary = beneficiary;
        nodeMapping[scsId].size = capacityMapping[weight];
        nodeMapping[scsId].lastVerifiedBlock = 0;
        unassignedNoteList.push(scsId);

        nodeListTemp = new address[](shardSize);
        nodeListIndex = new uint256[](shardSize);
        uint index = 0;
        uint256 shardId = 0;
        for(uint i = 0;i<unassignedNoteList.length; i++){
            if(nodeListTemp[shardSize-1] != address(0)){
                break;
            }
            else{
                if(nodeMapping[unassignedNoteList[i]].size == capacityMapping[weight]){
                    nodeListTemp[index] = unassignedNoteList[i];
                    nodeListIndex[index]=i;
                    index++;
                }
            }
        }
        // 如果同weight的nodes的数量=shardSize
        if(index == shardSize){
            shardId = addShard(weight);
            // 把所有的nodes加到nodeShardMapping里面
            for(uint j=0; j<nodeListTemp.length; j++){
                nodeShardMapping[nodeListTemp[j]] = shardMapping[shardId];
                nodeMapping[nodeListTemp[j]].shardId = shardId;
                nodeMapping[nodeListTemp[j]].lastVerifiedBlock = block.number + blockVerificationInterval + j;
            }
            //在nodeList删除已经分配的node
            for(uint k=0; k<nodeListIndex.length; k++){
                removeFromAddressArray(nodeListIndex[k]);
            }
        }
        return shardId;
    }

    function removeNode(address scsId) public returns (bool)
    {
        require(msg.sender == owner || admins[msg.sender] == 1 || msg.sender == scsId || msg.sender == nodeMapping[scsId].beneficiary);
        delete nodeMapping[scsId];
        return true;
    }

    function addFile(string fileHash, string fileName, uint256 fileSize, uint256 createTime,uint256 shardId) public returns (uint256)
    {
        return addFile(fileHash, fileName, fileSize, createTime, "",shardId);
    }

    function addFile(string fileHash, string fileName, uint256 fileSize, uint256 createTime, string ipfsId,uint256 shardId) public returns (uint256)
    {
        require(shardList.length > 0);
        uint256 fileId = fileList.length + 1;

        File memory aFile;
        aFile.fileId = fileId;
        aFile.fileHash = fileHash;
        aFile.fileName = fileName;
        aFile.fileSize = fileSize;
        aFile.fileOwner = msg.sender;
        aFile.createTime = createTime;
        aFile.verifiedCount = 0;

        fileList.push(fileId);
        fileMapping[fileId] = aFile;

//        uint256 shardId = addToShard(aFile);
        // update shardMapping(shardId).availableSize and percentage from file info.
        shardMapping[shardId].availableSize = shardMapping[shardId].availableSize - aFile.fileSize;
        shardMapping[shardId].percentage = (10 ** 10) * (shardMapping[shardId].size - shardMapping[shardId].availableSize)/shardMapping[shardId].size;

        shardFileList[shardId].push(fileId);
        myFileList[msg.sender].push(fileId);
        fileShardIdMapping[fileId] = shardId;

        PREC10.ipfsFile(fileHash, uint(AccessType.write), shardId, ipfsId);
        fileCount = fileList.length;
        return (fileId);
    }

    function removeFile(uint256 fileId) public returns (bool)
    {
        require(msg.sender == fileMapping[fileId].fileOwner && fileMapping[fileId].fileId != 0);
        removeFromShard(fileMapping[fileId]);

        fileMapping[fileId].fileId = 0;
        delete fileMapping[fileId];

        PREC10.ipfsFile(fileMapping[fileId].fileHash, uint(AccessType.remove), fileShardIdMapping[fileId], "");

        return true;
    }

    function readFile(uint256 fileId, string ipfsId) public returns (bool)
    {

        PREC10.ipfsFile(fileMapping[fileId].fileHash, uint(AccessType.read), fileShardIdMapping[fileId], ipfsId);

        return true;
    }

    function addToShard(File aFile) private returns (uint256)
    {
        uint256 shardId = 1;
        // loop through list, should be sorted.
        // if availableSize < a.File.size, go to next.
        // if shard id in recentlyUsedList, go to next.
        for (uint i = 0; i < shardList.length; i++) {
            if (shardMapping[shardList[i]].availableSize < aFile.fileSize){
                continue;
            }
            bool result = recentlyUsed(shardList[i]);
            if(result == true){
                continue;
            }else{
                shardId = shardList[i];
                break;
            }
        }
        // update recentlyUsedList
        if (shardList.length > 10 && recentlyUsedList.length >= min(shardList.length.div(10), 10)){
            removeFromArray(0);
            recentlyUsedList.push(shardId);
        }else{
			if(shardList.length > 10){
			 recentlyUsedList.push(shardId);
			}
        }
        // update shardMapping(shardId).availableSize and percentage from file info.
        shardMapping[shardId].availableSize = shardMapping[shardId].availableSize - aFile.fileSize;
        shardMapping[shardId].percentage = (10 ** 10) * (shardMapping[shardId].size - shardMapping[shardId].availableSize)/shardMapping[shardId].size;
        // sort shard list by percentag
        if(shardList.length >1){
            sortShardList(0, shardList.length-1);
        }
        // return selected shardId
        return shardId;
    }

    function removeFromShard(File aFile) private
    {
        // TO DO: update shard information based on file removal.
    }

    function getMyFileHashes(address myAddr) view public returns (uint256[]) {
        return myFileList[myAddr];
    }

    function getAllFilesByShard(uint256 shardId) view public returns (uint256[]) {
        return shardFileList[shardId];
    }

    function getAllShards() view public returns (uint256[]) {
        return shardList;
    }

    function getFileById(uint256 fileId) view public returns (string, string, uint256, address, uint256, uint) {
        return (fileMapping[fileId].fileHash,
        fileMapping[fileId].fileName,
        fileMapping[fileId].fileSize,
        fileMapping[fileId].fileOwner,
        fileMapping[fileId].createTime,
        fileMapping[fileId].verifiedCount);
    }

    function submitVerifyTransaction(address verifyGroupId, address verifyNodeId, uint256 blockNumber, string fileHash, uint256 shardId) {

        require(msg.sender == verifyNodeId);
        VerifyTransaction memory trans;
        trans.scsId = msg.sender;
        trans.verifyNodeId = msg.sender;
        trans.blockNumber = blockNumber;
        trans.fileHash = fileHash;
        trans.totalCount = shardMapping[shardId].nodeCount;
        trans.votedCount = 1;
        trans.affirmCount = 1;

        verifyGroupMapping[verifyGroupId].push(trans);
        nodeMapping[msg.sender].lastVerifiedBlock.add(blockVerificationInterval);
    }

    function voteVerifyTransaction(address verifyGroupId, address verifyNodeId, address votingNodeId, uint256 blockNumber,
        string fileHash, uint256 shardId) public returns (bool) {

        if(msg.sender != votingNodeId || verifyGroupMapping[verifyGroupId].length==0)
            return false;

        VerifyTransaction memory trans;
        trans.scsId = msg.sender;
        trans.verifyNodeId = verifyNodeId;
        trans.blockNumber = blockNumber;
        trans.fileHash = fileHash;
        trans.totalCount = verifyGroupMapping[verifyGroupId][0].totalCount;
        verifyGroupMapping[verifyGroupId][0].votedCount += 1;
        trans.votedCount = verifyGroupMapping[verifyGroupId][0].votedCount;

        if (compareStringsbyBytes(fileHash, verifyGroupMapping[verifyGroupId][0].fileHash)) {
            verifyGroupMapping[verifyGroupId][0].affirmCount += 1;
            trans.affirmCount = verifyGroupMapping[verifyGroupId][0].affirmCount;
        }

        verifyGroupMapping[verifyGroupId].push(trans);

        if (verifyGroupMapping[verifyGroupId][0].affirmCount > verifyGroupMapping[verifyGroupId][0].totalCount / 2)
        {
            address nodeId = verifyGroupMapping[verifyGroupId][0].scsId;
            address beneficiary = nodeMapping[nodeId].beneficiary;

            award(beneficiary);
        }
        return true;
    }

    function award(address beneficiary){
        // to do
    }

    function compareStringsbyBytes(string s1, string s2) private pure returns (bool)
    {
        bytes memory s1bytes = bytes(s1);
        bytes memory s2bytes = bytes(s2);
        if (s1bytes.length != s2bytes.length) {
            return false;
        }
        else {
            for (uint i = 0; i < s1bytes.length; i++)
            {
                if (s1bytes[i] != s2bytes[i])
                    return false;
            }
            return true;
        }
    }

    function removeFromArray(uint index) {
        if (index >= recentlyUsedList.length)
            return;

        for (uint i = index; i < recentlyUsedList.length - 1; i++) {
            recentlyUsedList[i] = recentlyUsedList[i + 1];
        }
        delete recentlyUsedList[recentlyUsedList.length - 1];
        //array.length--;
    }

    function removeFromAddressArray( uint index) {
        if (index >= unassignedNoteList.length)
            return;

        for (uint i = index; i < unassignedNoteList.length - 1; i++) {
            unassignedNoteList[i] = unassignedNoteList[i + 1];
        }
        delete unassignedNoteList[unassignedNoteList.length - 1];
        //array.length--;
    }

    function sortShardList(uint256 left, uint256 right) internal {
        uint256 i = left;
        uint256 j = right;
        uint256 pivot = left + (right - left) / 2;

        uint256 pivotValue = shardMapping[shardList[pivot]].percentage * shardMapping[shardList[pivot]].weight;
        while (i <= j) {
            while (shardMapping[shardList[i]].percentage * shardMapping[shardList[i]].weight < pivotValue) i++;
            while (pivotValue < shardMapping[shardList[j]].percentage * shardMapping[shardList[j]].weight) j--;
            if (i <= j) {
                (shardList[i], shardList[j]) = (shardList[j], shardList[i]);
                i++;
                j--;
            }
        }
        if (left < j)
            sortShardList(left, j);
        if (i < right)
            sortShardList(i, right);
    }

    function recentlyUsed(uint256 value) returns (bool) {
        for (uint i = 0; i < recentlyUsedList.length; i++) {
            if (value == recentlyUsedList[i]) {
                return true;
            }
        }
        return false;
    }

    function min(uint256 val1,uint256 val2) returns (uint256) {
        if(val1 <= val2){
            return val1;
        }else{
            return val2;
        }
    }
}


pragma solidity ^0.4.11;


/**
 * @title SafeMath
 * @dev Math operations with safety checks that revert on error
 */
library SafeMath {

    /**
    * @dev Multiplies two numbers, reverts on overflow.
    */
    function mul(uint256 _a, uint256 _b) internal pure returns (uint256) {
        // Gas optimization: this is cheaper than requiring 'a' not being zero, but the
        // benefit is lost if 'b' is also tested.
        // See: https://github.com/OpenZeppelin/openzeppelin-solidity/pull/522
        if (_a == 0) {
            return 0;
        }

        uint256 c = _a * _b;
        require(c / _a == _b);

        return c;
    }

    /**
    * @dev Integer division of two numbers truncating the quotient, reverts on division by zero.
    */
    function div(uint256 _a, uint256 _b) internal pure returns (uint256) {
        require(_b > 0);
        // Solidity only automatically asserts when dividing by 0
        uint256 c = _a / _b;
        // assert(_a == _b * c + _a % _b); // There is no case in which this doesn't hold

        return c;
    }

    /**
    * @dev Subtracts two numbers, reverts on overflow (i.e. if subtrahend is greater than minuend).
    */
    function sub(uint256 _a, uint256 _b) internal pure returns (uint256) {
        require(_b <= _a);
        uint256 c = _a - _b;

        return c;
    }

    /**
    * @dev Adds two numbers, reverts on overflow.
    */
    function add(uint256 _a, uint256 _b) internal pure returns (uint256) {
        uint256 c = _a + _b;
        require(c >= _a);

        return c;
    }

    /**
    * @dev Divides two numbers and returns the remainder (unsigned integer modulo),
    * reverts when dividing by zero.
    */
    function mod(uint256 a, uint256 b) internal pure returns (uint256) {
        require(b != 0);
        return a % b;
    }
}
```

FileStorm子链技术于2018年7月14日正式发布。
