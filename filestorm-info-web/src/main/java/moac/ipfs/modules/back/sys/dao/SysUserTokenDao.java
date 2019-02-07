package moac.ipfs.modules.back.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import moac.ipfs.modules.back.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2017-03-23 15:22:07
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);
	
}
