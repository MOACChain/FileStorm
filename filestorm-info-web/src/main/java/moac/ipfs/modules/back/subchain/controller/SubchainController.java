package moac.ipfs.modules.back.subchain.controller;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import moac.ipfs.modules.back.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import moac.ipfs.modules.back.subchain.entity.SubchainEntity;
import moac.ipfs.modules.back.subchain.service.SubchainService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-02 18:10:35
 */
@RestController
@RequestMapping("/back/subchain")
@Api("后台存储子链接口")
public class SubchainController extends AbstractController {
	@Autowired
	private SubchainService subchainService;
	
	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiOperation("查询子链列表")
    //@RequiresPermissions("subchain:subchain:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SubchainEntity> subchainList = subchainService.queryList(query);
		int total = subchainService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(subchainList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@GetMapping("/info/{subchainId}")
	@ApiOperation("查询子链详情")
    //@RequiresPermissions("subchain:subchain:info")
	public R info(@PathVariable("subchainId") Long subchainId){
		SubchainEntity subchain = subchainService.selectById(subchainId);
		
		return R.ok().put("subchain", subchain);
	}
	
	/**
	 * 保存
	 */
	@PostMapping("/add")
	@ApiOperation("新增存储子链")
    //@RequiresPermissions("subchain:subchain:save")
	public R save(@RequestBody SubchainEntity subchain){
		subchainService.add(subchain,getUser());
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperation("修改存储子链")
    //@RequiresPermissions("subchain:subchain:update")
	public R update(@RequestBody SubchainEntity subchain){
		subchainService.updateById(subchain);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@ApiOperation("删除存储子链")
    //@RequiresPermissions("subchain:subchain:delete")
	public R delete(@RequestBody SubchainEntity subchain){
		subchainService.delete(subchain,getUser());
		return R.ok();
	}

	/**
	 * 同步所有存储子链的数据
	 */
	@PostMapping("/syn")
	//@RequiresPermissions("subchain:subchain:delete")
	public R syn(){
		subchainService.synService(getUser());

		return R.ok();
	}


	
}
