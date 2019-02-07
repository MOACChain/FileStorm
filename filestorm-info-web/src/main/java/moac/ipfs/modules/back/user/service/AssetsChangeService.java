package moac.ipfs.modules.back.user.service;

import moac.ipfs.modules.back.user.entity.AssetsChangeEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 16:26:00
 */
public interface AssetsChangeService extends IService<AssetsChangeEntity>{
	
	List<AssetsChangeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] assetsChangeIds);
}
