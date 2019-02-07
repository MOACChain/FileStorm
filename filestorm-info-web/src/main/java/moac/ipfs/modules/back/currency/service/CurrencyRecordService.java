package moac.ipfs.modules.back.currency.service;

import moac.ipfs.modules.back.currency.entity.CurrencyRecordEntity;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:55:03
 */
public interface CurrencyRecordService extends IService<CurrencyRecordEntity>{
	
	List<CurrencyRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] currencyRecordIds);
}
