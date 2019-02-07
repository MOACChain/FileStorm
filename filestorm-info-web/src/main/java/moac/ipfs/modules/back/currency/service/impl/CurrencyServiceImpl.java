package moac.ipfs.modules.back.currency.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import moac.ipfs.common.exception.RRException;
import moac.ipfs.common.utils.MoacUtils;
import moac.ipfs.common.utils.Utils;
import moac.ipfs.modules.back.currency.entity.CurrencyRecordEntity;
import moac.ipfs.modules.back.currency.service.CurrencyRecordService;
import moac.ipfs.modules.back.user.entity.AssetsFormEntity;
import moac.ipfs.modules.back.user.entity.UserAssetsEntity;
import moac.ipfs.modules.back.user.entity.UserEntity;
import moac.ipfs.modules.back.user.service.UserAssetsService;
import moac.ipfs.modules.back.user.service.UserService;
import moac.ipfs.modules.web.service.BaseWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.currency.dao.CurrencyDao;
import moac.ipfs.modules.back.currency.entity.CurrencyEntity;
import moac.ipfs.modules.back.currency.service.CurrencyService;

@Service("currencyService")
public class CurrencyServiceImpl extends ServiceImpl<CurrencyDao, CurrencyEntity> implements CurrencyService {
	@Autowired
	private CurrencyDao currencyDao;
	@Autowired
	private UserService userService;
	@Autowired
	private MoacUtils moacUtils;
	@Autowired
	private CurrencyRecordService currencyRecordService;
	@Autowired
	private UserAssetsService userAssetsService;
	
	@Override
	public List<CurrencyEntity> queryList(Map<String, Object> map){
		return currencyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return currencyDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] currencyIds){
		currencyDao.deleteBatch(currencyIds);
	}

	@Override
	public void subChainCoinToFstService(AssetsFormEntity form) {
		UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>()
				.eq("address", form.getAddress()));
		if (!userEntity.getPassword().equals(form.getPassword())) {
			throw new RRException("密码不正确！");
		}
		BigDecimal balance = moacUtils.querySubChainBalance(userEntity.getAddress());
		if (balance.compareTo(form.getAmount()) < 0) {
			throw new RRException("COIN余额不足！");
		}
		CurrencyRecordEntity currencyRecordEntity = new CurrencyRecordEntity();
		JSONObject json = MoacUtils.subChainCoinToFst(userEntity.getAddress(),userEntity.getPassword(),userEntity.getKeyStore(),form.getAmount().toPlainString());
		JSONObject jsonObject = json.getJSONObject("resultData");
		currencyRecordEntity.setHash(jsonObject.getString("hash"));
		currencyRecordEntity.setAmount(form.getAmount());
		currencyRecordEntity.setCreateTime(System.currentTimeMillis());
		currencyRecordEntity.setCurrencyId(1L);
		currencyRecordEntity.setFee(new BigDecimal(0));
		currencyRecordEntity.setFromAddress(userEntity.getAddress());
		currencyRecordEntity.setNo("IPFS" + Utils.getSerialNo());
		currencyRecordEntity.setNotes("");
		currencyRecordEntity.setReceiveAddress(userEntity.getAddress());
		currencyRecordEntity.setUserId(userEntity.getUserId());
		currencyRecordEntity.setType(0);
		currencyRecordService.insert(currencyRecordEntity);
	}

	@Override
	public void fstToSubChainCoinService(AssetsFormEntity form) {
		UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>()
				.eq("address", form.getAddress()));
		if (!userEntity.getPassword().equals(form.getPassword())) {
			throw new RRException("密码不正确！");
		}
		BigDecimal balance = moacUtils.queryMainChainBalance(userEntity.getAddress());
		if (balance.compareTo(form.getAmount()) < 0) {
			throw new RRException("FST余额不足！");
		}
		CurrencyRecordEntity currencyRecordEntity = new CurrencyRecordEntity();
		JSONObject json = MoacUtils.subChainCoinToFst(userEntity.getAddress(),userEntity.getPassword(),userEntity.getKeyStore(),form.getAmount().toPlainString());
		JSONObject jsonObject = json.getJSONObject("resultData");
		currencyRecordEntity.setHash(jsonObject.getString("hash"));
		currencyRecordEntity.setAmount(form.getAmount());
		currencyRecordEntity.setCreateTime(System.currentTimeMillis());
		currencyRecordEntity.setCurrencyId(1L);
		currencyRecordEntity.setFee(new BigDecimal(0));
		currencyRecordEntity.setFromAddress(userEntity.getAddress());
		currencyRecordEntity.setNo("IPFS" + Utils.getSerialNo());
		currencyRecordEntity.setNotes("");
		currencyRecordEntity.setReceiveAddress(userEntity.getAddress());
		currencyRecordEntity.setUserId(userEntity.getUserId());
		currencyRecordEntity.setType(0);
		currencyRecordService.insert(currencyRecordEntity);
	}

	@Override
	public UserAssetsEntity queryBalanceService(AssetsFormEntity form) {
		UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>().eq("address", form.getAddress()));
		UserAssetsEntity userAssetsEntity = userAssetsService.selectOne(new EntityWrapper<UserAssetsEntity>()
				.eq("currency_number", form.getCurrencyNumber())
				.eq("user_id", userEntity.getUserId()));
		if ("FST".equals(userAssetsEntity.getCurrencyNumber())) {
			BigDecimal balance = moacUtils.queryMainChainBalance(userEntity.getAddress());
			userAssetsEntity.setBalance(balance);
		} else if ("COIN".equals(userAssetsEntity.getCurrencyNumber())) {
			BigDecimal balance = moacUtils.querySubChainBalance(userEntity.getAddress());
			userAssetsEntity.setBalance(balance);
		} else {
			throw new RRException("查询余额错误！");
		}
		return userAssetsEntity;
	}

}
