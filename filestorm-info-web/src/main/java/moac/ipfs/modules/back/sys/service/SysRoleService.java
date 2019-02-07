package moac.ipfs.modules.back.sys.service;

import com.baomidou.mybatisplus.service.IService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.modules.back.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void save(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
