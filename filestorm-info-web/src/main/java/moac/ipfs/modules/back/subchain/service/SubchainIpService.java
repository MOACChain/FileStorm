package moac.ipfs.modules.back.subchain.service;

import moac.ipfs.modules.back.subchain.entity.SubchainIpEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-19 09:30:41
 */
public interface SubchainIpService extends IService<SubchainIpEntity>{
	
	List<SubchainIpEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Integer[] ids);

	/**
	 * 新增矿机节点
	 * @param subchainIp
	 */
    void addService(SubchainIpEntity subchainIp);

	/**
	 * 每月末矿池分红
	 */
	void minePoolNodeDividend();
}
