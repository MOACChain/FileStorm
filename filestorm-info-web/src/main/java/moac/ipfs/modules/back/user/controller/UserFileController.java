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

import moac.ipfs.modules.back.user.entity.UserFileEntity;
import moac.ipfs.modules.back.user.service.UserFileService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-02 18:07:06
 */
@RestController
@RequestMapping("/user/userfile")
public class UserFileController {
	@Autowired
	private UserFileService userFileService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("user:userfile:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<UserFileEntity> userFileList = userFileService.queryList(query);
		int total = userFileService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userFileList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{fileId}")
    //@RequiresPermissions("user:userfile:info")
	public R info(@PathVariable("fileId") Long fileId){
		UserFileEntity userFile = userFileService.selectById(fileId);
		
		return R.ok().put("userFile", userFile);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("user:userfile:save")
	public R save(@RequestBody UserFileEntity userFile){
		userFileService.insert(userFile);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("user:userfile:update")
	public R update(@RequestBody UserFileEntity userFile){
		userFileService.updateById(userFile);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("user:userfile:delete")
	public R delete(@RequestBody Long[] fileIds){
		userFileService.deleteBatch(fileIds);
		
		return R.ok();
	}
	
}
