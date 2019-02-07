package moac.ipfs.modules.back.storagePackage.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import moac.ipfs.modules.back.storagePackage.entity.StoragePackageEntity;
import moac.ipfs.modules.back.storagePackage.service.StoragePackageService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-27 10:19:35
 */
@RestController
@RequestMapping("/storagePackage/storagepackage")
@EnableScheduling
public class StoragePackageController {
	@Autowired
	private StoragePackageService storagePackageService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("storagePackage:storagepackage:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<StoragePackageEntity> storagePackageList = storagePackageService.queryList(query);
		int total = storagePackageService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(storagePackageList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{storagePackageId}")
    //@RequiresPermissions("storagePackage:storagepackage:info")
	public R info(@PathVariable("storagePackageId") Long storagePackageId){
		StoragePackageEntity storagePackage = storagePackageService.selectById(storagePackageId);
		
		return R.ok().put("storagePackage", storagePackage);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("storagePackage:storagepackage:save")
	public R save(@RequestBody StoragePackageEntity storagePackage){
		storagePackageService.insert(storagePackage);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("storagePackage:storagepackage:update")
	public R update(@RequestBody StoragePackageEntity storagePackage){
		storagePackageService.updateById(storagePackage);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("storagePackage:storagepackage:delete")
	public R delete(@RequestBody Long[] storagePackageIds){
		storagePackageService.deleteBatch(storagePackageIds);
		
		return R.ok();
	}

	/**
	 * 每月套餐状态更新
	 */
	@Scheduled(cron = "0 0 1 1 * ?")
	public void updatePackageStatus(){
		storagePackageService.queryUpdatePackage();
	}

	/**
	 * 套餐购买状态更新
	 */
//	@Scheduled(initialDelay = 10000,fixedDelay = 1000 * 30)
	public void updatePackageBuyStatus(){
		storagePackageService.updatePackageBuyStatus();
	}
}
