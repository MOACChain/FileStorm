package moac.ipfs.modules.back.user.controller;

import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;
import moac.ipfs.modules.back.user.entity.UserEntity;
import moac.ipfs.modules.back.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-09-13 10:45:37
 */
@RestController
@RequestMapping("/back/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("user:user:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<UserEntity> userList = userService.queryList(query);
		int total = userService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{userId}")
    //@RequiresPermissions("user:user:info")
	public R info(@PathVariable("userId") Long userId){
		UserEntity user = userService.selectById(userId);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("user:user:save")
	public R save(@RequestBody UserEntity user){
		userService.insert(user);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("user:user:update")
	public R update(@RequestBody UserEntity user){
		userService.updateById(user);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("user:user:delete")
	public R delete(@RequestBody Long[] userIds){
		userService.deleteBatch(userIds);
		
		return R.ok();
	}
	
}
