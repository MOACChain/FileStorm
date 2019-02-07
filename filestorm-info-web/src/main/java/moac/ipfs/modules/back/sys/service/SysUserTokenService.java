package moac.ipfs.modules.back.sys.service;

import com.baomidou.mybatisplus.service.IService;
import moac.ipfs.common.utils.R;
import moac.ipfs.modules.back.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2017-03-23 15:22:07
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
