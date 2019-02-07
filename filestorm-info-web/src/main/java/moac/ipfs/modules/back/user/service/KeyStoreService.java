package moac.ipfs.modules.back.user.service;

import moac.ipfs.modules.back.user.entity.KeyStoreEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-23 16:02:55
 */
public interface KeyStoreService extends IService<KeyStoreEntity>{
	
	List<KeyStoreEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] keyStoreIds);
}
