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

import moac.ipfs.modules.back.gain.entity.BlockHeightEntity;
import moac.ipfs.modules.back.gain.service.BlockHeightService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 16:33:43
 */
@RestController
@RequestMapping("/gain/blockheight")
public class BlockHeightController {
	@Autowired
	private BlockHeightService blockHeightService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("gain:blockheight:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BlockHeightEntity> blockHeightList = blockHeightService.queryList(query);
		int total = blockHeightService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(blockHeightList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{blockHeightId}")
    //@RequiresPermissions("gain:blockheight:info")
	public R info(@PathVariable("blockHeightId") Long blockHeightId){
		BlockHeightEntity blockHeight = blockHeightService.selectById(blockHeightId);
		
		return R.ok().put("blockHeight", blockHeight);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("gain:blockheight:save")
	public R save(@RequestBody BlockHeightEntity blockHeight){
		blockHeightService.insert(blockHeight);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("gain:blockheight:update")
	public R update(@RequestBody BlockHeightEntity blockHeight){
		blockHeightService.updateById(blockHeight);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("gain:blockheight:delete")
	public R delete(@RequestBody Long[] blockHeightIds){
		blockHeightService.deleteBatch(blockHeightIds);
		
		return R.ok();
	}
	
}
