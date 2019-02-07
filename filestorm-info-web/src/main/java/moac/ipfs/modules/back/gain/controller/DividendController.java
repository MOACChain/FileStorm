package moac.ipfs.modules.back.gain.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import moac.ipfs.modules.back.gain.entity.DividendEntity;
import moac.ipfs.modules.back.gain.service.DividendService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-30 15:02:08
 */
@RestController
@RequestMapping("/gain/dividend")
public class DividendController {
	@Autowired
	private DividendService dividendService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("gain:dividend:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<DividendEntity> dividendList = dividendService.queryList(query);
		int total = dividendService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(dividendList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{dividendId}")
    //@RequiresPermissions("gain:dividend:info")
	public R info(@PathVariable("dividendId") Long dividendId){
		DividendEntity dividend = dividendService.selectById(dividendId);
		
		return R.ok().put("dividend", dividend);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("gain:dividend:save")
	public R save(@RequestBody DividendEntity dividend){
		dividendService.insert(dividend);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("gain:dividend:update")
	public R update(@RequestBody DividendEntity dividend){
		dividendService.updateById(dividend);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("gain:dividend:delete")
	public R delete(@RequestBody Long[] dividendIds){
		dividendService.deleteBatch(dividendIds);
		
		return R.ok();
	}
	
}
