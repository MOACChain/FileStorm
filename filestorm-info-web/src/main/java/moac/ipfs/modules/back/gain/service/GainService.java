package moac.ipfs.modules.back.gain.service;

import moac.ipfs.modules.back.gain.entity.GainEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:57:21
 */
public interface GainService extends IService<GainEntity>{
	
	List<GainEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] gainIds);
}
