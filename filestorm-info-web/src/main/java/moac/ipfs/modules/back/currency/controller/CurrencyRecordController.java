package moac.ipfs.modules.back.currency.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import moac.ipfs.modules.back.currency.entity.CurrencyRecordEntity;
import moac.ipfs.modules.back.currency.service.CurrencyRecordService;
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
@RequestMapping("/currency/currencyrecord")
public class CurrencyRecordController {
	@Autowired
	private CurrencyRecordService currencyRecordService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("currency:currencyrecord:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CurrencyRecordEntity> currencyRecordList = currencyRecordService.queryList(query);
		int total = currencyRecordService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(currencyRecordList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{currencyRecordId}")
    //@RequiresPermissions("currency:currencyrecord:info")
	public R info(@PathVariable("currencyRecordId") Long currencyRecordId){
		CurrencyRecordEntity currencyRecord = currencyRecordService.selectById(currencyRecordId);
		
		return R.ok().put("currencyRecord", currencyRecord);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("currency:currencyrecord:save")
	public R save(@RequestBody CurrencyRecordEntity currencyRecord){
		currencyRecordService.insert(currencyRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("currency:currencyrecord:update")
	public R update(@RequestBody CurrencyRecordEntity currencyRecord){
		currencyRecordService.updateById(currencyRecord);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("currency:currencyrecord:delete")
	public R delete(@RequestBody Long[] currencyRecordIds){
		currencyRecordService.deleteBatch(currencyRecordIds);
		
		return R.ok();
	}
	
}
