# FileStorm 参与教程：

FileStorm是基于MOAC（墨客）区块链技术，把IPFS协议付诸实践的一个分布式存储平台。参与FileStorm需要安装FileStorm节点（俗称矿机）程序，然后到FileStorm平台注册，参与提供存储服务（挖矿）。**注意：公测期间本程序只能连墨客测试网**

## FileStorm 节点程序安装

公测期间暂时只支持Linux系统。至少2核CPU，4GB内存，硬盘支持1TB和8TB两种。（将来会开放更多硬盘大小选择）

建议大家选用下面两种操作系统
* CentOs 7.4 64位 (安装包需要用到make, gcc和glibc，所以建议安装完整版)
* Ubuntu 18.04 LTS

### 单节点测试：

如果用户只有单个挖矿节点参与测试，只需要准备一台机器。然后执行下面的流程。

1. 下载filestorm.zip并解压.
1. 进入到解压后的文件夹filestorm.
1. 运行`./filestorm_installation.sh`
1. 打开同目录下的userconfig.json文件，将beneficiary改成你自己的收益地址。**这个很重要，关系到你是否能获得挖矿收益。**如果还没有收益地址，可以拖到下面注册矿机一节，看如何生成地址。
```
{
    ...
    "Beneficiary": "0x13512e62c8Cb1De316F7C442d767d0365350720c",
    ...
}
```
5. 运行`./stop_filestorm_scs.sh` 停止程序
5. 运行`./run_filestorm_scs.sh` 重启
5. 打开服务器端口4001，5001，8080
5. 可以用`tail -f100 scs.out`查看节点运行状况
5. 可以用`tail -f100 ipfs.out`查看ipfs运行状况

### 多节点测试：

每个挖矿节点需要连接到一个MOAC Vnode，vnode节点把挖矿节点跟MOAC区块链连接，接受和发送区块链交易。公测期间，FileStorm免费提供vnode，上主网后，vnode会从跟它连接的FileStorm矿机收取手续费用。（我们也欢迎大矿场公开自己的vnode给大家使用，赚取手续费。）如果用户进行多节点测试，建议每10个FileStorm节点接一个自己的vnode节点。这个节点不参与存储挖矿，也不需要装大存储硬盘。甚至可以安装在某一个FileStorm挖矿节点上。

#### Vnode节点安装

1. 下载filestorm-vnode.zip并解压.
1. 进入vnode文件夹。
1. 打开服务器端口50062和8545
1. 运行`nohup ./moac-linux-amd64 --testnet --rpc &`
1. 第一次运行vnode，需要跟MOAC区块链同步，可能需要几个小时。

#### FileStorm节点安装

1. 下载filestorm.zip并解压.
1. 进入到解压后的文件夹filestorm.
1. 运行`./filestorm_installation.sh`
1. 打开服务器端口4001，5001，8080
1. 打开同目录下的userconfig.json文件，将VnodeServiceCfg改成[上面的Vnode IP]:50062。
1. 将beneficiary改成你自己的收益地址。**这个很重要，关系到你是否能获得挖矿收益。**如果还没有收益地址，可以拖到下面注册矿机一节，看如何生成地址。
```
{
    ...
    "Beneficiary": "0x13512e62c8Cb1De316F7C442d767d0365350720c",
    ...
}
```
5. 运行`./stop_filestorm_scs.sh` 停止程序
5. 运行`./run_filestorm_scs.sh` 重启
5. 打开服务器端口4001，5001，8080
5. 可以用`tail -f100 scs.out`查看节点运行状况
5. 可以用`tail -f100 ipfs.out`查看ipfs运行状况


## 注册矿机

1. 到 http://www.FileStorm.info 网站，注册成为新用户。
1. 如果已经有墨客钱包的朋友，也可以在注册时导入墨客钱包地址到账号中。
1. 登录后点击右上角的地址，就可以看到自己的地址和keystore, 私钥等信息。这个地址，可以设成你的beneficiary地址。
1. 点击正上方菜单里的参与挖矿进入矿机注册界面。
1. 填写注册信息，然后点击“立即参与”。
* 矿机IP - 请输入自己的挖矿节点的IP地址。如果是多节点测试使用的自己的vnode，此处不填。
* SCS ID - 在矿机上filestorm/scskeystore中，你会找到一个文件名像这样的文件：UTC--2018-09-23T05-52-26.554142261Z--e09f56c0c8c528b14ea594764c09c7ede73f88c0，--后面那一长串字符，前面加上“0x"，就是SCS ID
* Beneficiary - 就是你的登录地址。
* 硬盘大小 - 根据自己参与的硬盘大小选择1TB或者8TB，将来会开放更多。
* 押金 - 因为是使用墨客区块链，所以需要每个节点需要放若干个MOAC做为押金，在节点不工作或者作恶时，押金会被扣掉。
* 请确认以上信息 - 必须选中复选框，同意我们所有制定的公测规则，才能参与。
6. 公测期间，可以点击矿机注册界面右下角的“点我领取Moac"链接，用登录账号使用的地址领取Moac。
6. 注册成功后，矿机就被加入备选节点池，等待挖矿。等待的时间根据备选节点池中备选节点数而定，短则几分钟，长可达数天。


## 奖励方式

所有参与测试的矿机都可以得到测试网的FST奖励，暂时的设定是每台矿机每40个区块（约7分钟）可得0.25个FST。挖矿所得的测试网FST，将来可以按一定的比例兑换成主网FST通证（暂定2:1）。


## 查看收益

在 http://www.FileStorm.info 网站上点击右上角地址，再点击查看资产，就能看到挖矿收益。收益是以Coin的形式发放。每天发放一次。这个Coin可以用FST提出，将来可以在交易所兑换成其他资产。


## 更多链接

FileStorm官网
http://www.FileStorm.info 

墨客基金会科普文
https://mp.weixin.qq.com/s/MOCeodmBU0QqiDO-Zg9OIA

FileStorm爱好者写的安装教程
https://www.jianshu.com/p/2ef4d259be90

