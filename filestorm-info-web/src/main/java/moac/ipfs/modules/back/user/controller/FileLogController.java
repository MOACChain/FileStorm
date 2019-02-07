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

import moac.ipfs.modules.back.user.entity.FileLogEntity;
import moac.ipfs.modules.back.user.service.FileLogService;
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
@RequestMapping("/user/filelog")
public class FileLogController {
	@Autowired
	private FileLogService fileLogService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
    //@RequiresPermissions("user:filelog:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<FileLogEntity> fileLogList = fileLogService.queryList(query);
		int total = fileLogService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(fileLogList, total, query.getLimit(), query.getPage().getCurrent());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{logId}")
    //@RequiresPermissions("user:filelog:info")
	public R info(@PathVariable("logId") Long logId){
		FileLogEntity fileLog = fileLogService.selectById(logId);
		
		return R.ok().put("fileLog", fileLog);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
    //@RequiresPermissions("user:filelog:save")
	public R save(@RequestBody FileLogEntity fileLog){
		fileLogService.insert(fileLog);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    //@RequiresPermissions("user:filelog:update")
	public R update(@RequestBody FileLogEntity fileLog){
		fileLogService.updateById(fileLog);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
    //@RequiresPermissions("user:filelog:delete")
	public R delete(@RequestBody Long[] logIds){
		fileLogService.deleteBatch(logIds);
		
		return R.ok();
	}
	
}
