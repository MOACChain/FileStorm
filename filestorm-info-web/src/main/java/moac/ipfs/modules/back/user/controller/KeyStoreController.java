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

import moac.ipfs.modules.back.user.entity.KeyStoreEntity;
import moac.ipfs.modules.back.user.service.KeyStoreService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-23 16:02:55
 */
@RestController
@RequestMapping("/user/keystore")
public class KeyStoreController {
	@Autowired
	private KeyStoreService keyStoreService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("user:keystore:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<KeyStoreEntity> keyStoreList = keyStoreService.queryList(query);
		int total = keyStoreService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(keyStoreList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{keyStoreId}")
    //@RequiresPermissions("user:keystore:info")
	public R info(@PathVariable("keyStoreId") Long keyStoreId){
		KeyStoreEntity keyStore = keyStoreService.selectById(keyStoreId);
		
		return R.ok().put("keyStore", keyStore);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("user:keystore:save")
	public R save(@RequestBody KeyStoreEntity keyStore){
		keyStoreService.insert(keyStore);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("user:keystore:update")
	public R update(@RequestBody KeyStoreEntity keyStore){
		keyStoreService.updateById(keyStore);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("user:keystore:delete")
	public R delete(@RequestBody Long[] keyStoreIds){
		keyStoreService.deleteBatch(keyStoreIds);
		
		return R.ok();
	}
	
}
