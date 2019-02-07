package moac.ipfs.modules.back.currency.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.currency.dao.CurrencyRecordDao;
import moac.ipfs.modules.back.currency.entity.CurrencyRecordEntity;
import moac.ipfs.modules.back.currency.service.CurrencyRecordService;

@Service("currencyRecordService")
public class CurrencyRecordServiceImpl extends ServiceImpl<CurrencyRecordDao, CurrencyRecordEntity> implements CurrencyRecordService {
	@Autowired
	private CurrencyRecordDao currencyRecordDao;
	
	@Override
	public List<CurrencyRecordEntity> queryList(Map<String, Object> map){
		return currencyRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return currencyRecordDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] currencyRecordIds){
		currencyRecordDao.deleteBatch(currencyRecordIds);
	}
	
}
