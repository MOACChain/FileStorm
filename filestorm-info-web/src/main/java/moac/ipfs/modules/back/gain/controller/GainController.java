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

import moac.ipfs.modules.back.gain.entity.GainEntity;
import moac.ipfs.modules.back.gain.service.GainService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:57:21
 */
@RestController
@RequestMapping("/gain/gain")
public class GainController {
	@Autowired
	private GainService gainService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("gain:gain:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<GainEntity> gainList = gainService.queryList(query);
		int total = gainService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(gainList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{gainId}")
    //@RequiresPermissions("gain:gain:info")
	public R info(@PathVariable("gainId") Long gainId){
		GainEntity gain = gainService.selectById(gainId);
		
		return R.ok().put("gain", gain);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("gain:gain:save")
	public R save(@RequestBody GainEntity gain){
		gainService.insert(gain);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("gain:gain:update")
	public R update(@RequestBody GainEntity gain){
		gainService.updateById(gain);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("gain:gain:delete")
	public R delete(@RequestBody Long[] gainIds){
		gainService.deleteBatch(gainIds);
		
		return R.ok();
	}
	
}
