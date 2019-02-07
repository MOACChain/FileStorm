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

import moac.ipfs.modules.back.user.entity.AssetsChangeEntity;
import moac.ipfs.modules.back.user.service.AssetsChangeService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 16:26:00
 */
@RestController
@RequestMapping("/user/assetschange")
public class AssetsChangeController {
	@Autowired
	private AssetsChangeService assetsChangeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("user:assetschange:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<AssetsChangeEntity> assetsChangeList = assetsChangeService.queryList(query);
		int total = assetsChangeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(assetsChangeList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{assetsChangeId}")
    //@RequiresPermissions("user:assetschange:info")
	public R info(@PathVariable("assetsChangeId") Long assetsChangeId){
		AssetsChangeEntity assetsChange = assetsChangeService.selectById(assetsChangeId);
		
		return R.ok().put("assetsChange", assetsChange);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("user:assetschange:save")
	public R save(@RequestBody AssetsChangeEntity assetsChange){
		assetsChangeService.insert(assetsChange);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("user:assetschange:update")
	public R update(@RequestBody AssetsChangeEntity assetsChange){
		assetsChangeService.updateById(assetsChange);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("user:assetschange:delete")
	public R delete(@RequestBody Long[] assetsChangeIds){
		assetsChangeService.deleteBatch(assetsChangeIds);
		
		return R.ok();
	}
	
}
