package moac.ipfs.modules.back.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import moac.ipfs.modules.back.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色管理
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2016年9月18日 上午9:33:33
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
