package moac.ipfs.modules.back.currency.controller;

import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.user.entity.AssetsFormEntity;
import moac.ipfs.modules.back.user.entity.UserAssetsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import moac.ipfs.modules.back.currency.entity.CurrencyEntity;
import moac.ipfs.modules.back.currency.service.CurrencyService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:55:03
 */
@RestController
@RequestMapping("/back/currency")
public class CurrencyController {
	@Autowired
	private CurrencyService currencyService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("currency:currency:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CurrencyEntity> currencyList = currencyService.queryList(query);
		int total = currencyService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(currencyList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{currencyId}")
    //@RequiresPermissions("currency:currency:info")
	public R info(@PathVariable("currencyId") Long currencyId){
		CurrencyEntity currency = currencyService.selectById(currencyId);
		
		return R.ok().put("currency", currency);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("currency:currency:save")
	public R save(@RequestBody CurrencyEntity currency){
		currencyService.insert(currency);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("currency:currency:update")
	public R update(@RequestBody CurrencyEntity currency){
		currencyService.updateById(currency);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("currency:currency:delete")
	public R delete(@RequestBody Long[] currencyIds){
		currencyService.deleteBatch(currencyIds);
		
		return R.ok();
	}

	/**
	 * 子链coin兑换fst
	 */
	@RequestMapping("/subChainCoinToFst")
	public R subChainCoinToFst(@RequestBody AssetsFormEntity form){
		currencyService.subChainCoinToFstService(form);
		return R.ok("兑换成功");
	}

	/**
	 * fst兑换子链coin
	 */
	@RequestMapping("/fstToSubChainCoin")
	public R fstToSubChainCoin(@RequestBody AssetsFormEntity form){
		currencyService.fstToSubChainCoinService(form);
		return R.ok("兑换成功");
	}

	/**
	 * 用户查询余额
	 * @param form
	 * @return
	 */
	@RequestMapping("queryBalance")
	public R queryBalance(@RequestBody AssetsFormEntity form){
		UserAssetsEntity userAssetsEntity =currencyService.queryBalanceService(form);
		return R.ok("查询余额成功！").put("userAssets",userAssetsEntity);
	}
}
