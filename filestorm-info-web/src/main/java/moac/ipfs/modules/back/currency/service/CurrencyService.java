package moac.ipfs.modules.back.currency.service;

import moac.ipfs.modules.back.currency.entity.CurrencyEntity;
import com.baomidou.mybatisplus.service.IService;
import moac.ipfs.modules.back.user.entity.AssetsFormEntity;
import moac.ipfs.modules.back.user.entity.UserAssetsEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:55:03
 */
public interface CurrencyService extends IService<CurrencyEntity>{
	
	List<CurrencyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] currencyIds);

	/**
	 * 子链coin兑换fst
	 * @param form
	 */
    void subChainCoinToFstService(AssetsFormEntity form);

	/**
	 * fst兑换子链coin
	 * @param form
	 */
	void fstToSubChainCoinService(AssetsFormEntity form);

	/**
	 * 查询余额
	 * @param form
	 * @return
	 */
	UserAssetsEntity queryBalanceService(AssetsFormEntity form);
}
