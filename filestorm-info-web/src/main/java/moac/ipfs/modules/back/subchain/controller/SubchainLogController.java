package moac.ipfs.modules.back.subchain.controller;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import moac.ipfs.modules.back.subchain.entity.SubchainLogEntity;
import moac.ipfs.modules.back.subchain.service.SubchainLogService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-09 10:27:30
 */
@RestController
@RequestMapping("/back/subchainlog")
@Api("后台存储子链操作日志接口")
public class SubchainLogController {
	@Autowired
	private SubchainLogService subchainLogService;
	
	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiOperation("查询子链操作日志列表")
    //@RequiresPermissions("subchain:subchainlog:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SubchainLogEntity> subchainLogList = subchainLogService.queryList(query);
		int total = subchainLogService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(subchainLogList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{subLogId}")
    //@RequiresPermissions("subchain:subchainlog:info")
	public R info(@PathVariable("subLogId") Integer subLogId){
		SubchainLogEntity subchainLog = subchainLogService.selectById(subLogId);
		
		return R.ok().put("subchainLog", subchainLog);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("subchain:subchainlog:save")
	public R save(@RequestBody SubchainLogEntity subchainLog){
		subchainLogService.insert(subchainLog);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("subchain:subchainlog:update")
	public R update(@RequestBody SubchainLogEntity subchainLog){
		subchainLogService.updateById(subchainLog);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("subchain:subchainlog:delete")
	public R delete(@RequestBody Integer[] subLogIds){
		subchainLogService.deleteBatch(subLogIds);
		
		return R.ok();
	}
	
}
