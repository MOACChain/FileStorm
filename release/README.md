## FileStorm 安装教程：

公测期间暂时只支持Linux系统。

### 单节点测试：

1. 下载filestorm.zip并解压.
2. 运行filestorm_installation.sh
3. 打开userconfig.json，将beneficiary改成你自己的收益地址。**这个很重要，关系到你是否能获得挖矿收益**
4. 运行stop_filestorm_scs.sh
5. 运行run_filestorm_scs.sh

### 多节点测试：

多节点测试，每10个FileStorm节点需要接一个MOAC vnode节点。所以需要先装一个vnode节点。这个节点为FileStorm节点提供通信，它不参与存储挖矿，也不需要装大存储硬盘。

Vnode节点安装

1. 下载filestorm.zip并解压.
2. 运行`nohup ./moac-linux-amd64 --testnet --rpc &`
3. 打开服务器端口50062和8545

FileStorm节点安装

1. 下载filestorm.zip并解压.
2. 运行filestorm_installation.sh
3. 打开userconfig.json，将VnodeServiceCfg改成[上面的Vnode IP]:50062。
4. 将beneficiary改成你自己的收益地址。**这个很重要，关系到你是否能获得挖矿收益**
5. 打开run_filestorm_scs，将第12行`--vnode-address http://120.78.2.59:8547`改成`--vnode-address http://[上面的Vnode IP]:8545`
6. 运行stop_filestorm_scs.sh
7. 运行run_filestorm_scs.sh

