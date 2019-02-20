## FileStorm 安装教程：

公测期间暂时只支持Linux系统。建议大家下载
CentOs 7.4 64位 (安装包需要用到make, gcc和glibc，所以建议安装完整版)
Ubuntu 18.04 LTS

**公测期间只能连墨客测试网**

### 单节点测试：

1. 下载filestorm.zip并解压.
1. 进入到解压后的文件夹filestorm.
1. 运行`./filestorm_installation.sh`
1. 打开同目录下的userconfig.json文件，将beneficiary改成你自己的收益地址。**这个很重要，关系到你是否能获得挖矿收益**
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

多节点测试，每10个FileStorm节点需要接一个MOAC vnode节点。所以需要先装一个vnode节点。这个节点为FileStorm节点提供通信，它不参与存储挖矿，也不需要装大存储硬盘。Vnode可以跟某一个FileStorm节点公用。

#### Vnode节点安装

1. 下载filestorm-vnode.zip并解压.
1. 进入vnode文件夹。
1. 运行`nohup ./moac-linux-amd64 --testnet --rpc &`
1. 打开服务器端口50062和8545

#### FileStorm节点安装

1. 下载filestorm.zip并解压.
1. 进入到解压后的文件夹filestorm.
1. 运行`./filestorm_installation.sh`
1. 打开服务器端口4001，5001，8080
1. 打开同目录下的userconfig.json文件，将VnodeServiceCfg改成[上面的Vnode IP]:50062。
1. 将beneficiary改成你自己的收益地址。**这个很重要，关系到你是否能获得挖矿收益**
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

### 更多链接

FileStorm官网
http://www.filestorm.info 

墨客基金会科普文
https://mp.weixin.qq.com/s/MOCeodmBU0QqiDO-Zg9OIA

爱好者写的基于CentOS的安装教程
https://www.jianshu.com/p/2ef4d259be90

