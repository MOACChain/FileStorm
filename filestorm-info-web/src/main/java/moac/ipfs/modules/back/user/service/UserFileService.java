package moac.ipfs.modules.back.user.service;

import moac.ipfs.modules.back.user.entity.UserFileEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-02 18:07:06
 */
public interface UserFileService extends IService<UserFileEntity>{
	
	List<UserFileEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] fileIds);
}
