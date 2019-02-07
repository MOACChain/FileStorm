package moac.ipfs.modules.back.subchain.service;

import moac.ipfs.modules.back.subchain.entity.SubchainLogEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-09 10:27:30
 */
public interface SubchainLogService extends IService<SubchainLogEntity>{
	
	List<SubchainLogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Integer[] subLogIds);
}
