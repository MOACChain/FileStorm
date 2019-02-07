package moac.ipfs.modules.back.gain.service;

import moac.ipfs.modules.back.gain.entity.BlockHeightEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 16:33:43
 */
public interface BlockHeightService extends IService<BlockHeightEntity>{
	
	List<BlockHeightEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] blockHeightIds);
}
