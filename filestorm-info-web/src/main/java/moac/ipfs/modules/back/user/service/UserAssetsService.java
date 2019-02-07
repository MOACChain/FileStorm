package moac.ipfs.modules.back.user.service;

import moac.ipfs.modules.back.user.entity.UserAssetsEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:52:21
 */
public interface UserAssetsService extends IService<UserAssetsEntity>{
	
	List<UserAssetsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] assetsIds);
}
