package moac.ipfs.modules.back.user.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import moac.ipfs.modules.back.user.entity.UserAssetsEntity;
import moac.ipfs.modules.back.user.service.UserAssetsService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:52:21
 */
@RestController
@RequestMapping("/user/userassets")
public class UserAssetsController {
	@Autowired
	private UserAssetsService userAssetsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("user:userassets:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<UserAssetsEntity> userAssetsList = userAssetsService.queryList(query);
		int total = userAssetsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userAssetsList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{assetsId}")
    //@RequiresPermissions("user:userassets:info")
	public R info(@PathVariable("assetsId") Long assetsId){
		UserAssetsEntity userAssets = userAssetsService.selectById(assetsId);
		
		return R.ok().put("userAssets", userAssets);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("user:userassets:save")
	public R save(@RequestBody UserAssetsEntity userAssets){
		userAssetsService.insert(userAssets);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("user:userassets:update")
	public R update(@RequestBody UserAssetsEntity userAssets){
		userAssetsService.updateById(userAssets);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("user:userassets:delete")
	public R delete(@RequestBody Long[] assetsIds){
		userAssetsService.deleteBatch(assetsIds);
		
		return R.ok();
	}
	
}
