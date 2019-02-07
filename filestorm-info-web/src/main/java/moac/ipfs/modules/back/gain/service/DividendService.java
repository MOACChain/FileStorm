package moac.ipfs.modules.back.gain.service;

import moac.ipfs.modules.back.gain.entity.DividendEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-30 15:02:08
 */
public interface DividendService extends IService<DividendEntity>{
	
	List<DividendEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] dividendIds);
}
