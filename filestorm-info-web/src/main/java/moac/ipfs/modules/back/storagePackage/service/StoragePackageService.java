package moac.ipfs.modules.back.storagePackage.service;

import moac.ipfs.modules.back.storagePackage.entity.StoragePackageEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-27 10:19:35
 */
public interface StoragePackageService extends IService<StoragePackageEntity>{
	
	List<StoragePackageEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] storagePackageIds);

	/**
	 * 查询需要更新状态的套餐
	 * @return
	 */
	void queryUpdatePackage();

	/**
	 * 更新套餐购买状态
	 */
	void updatePackageBuyStatus();
}
