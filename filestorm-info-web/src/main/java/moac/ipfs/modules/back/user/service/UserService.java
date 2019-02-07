package moac.ipfs.modules.back.user.service;

import com.baomidou.mybatisplus.service.IService;
import moac.ipfs.modules.back.user.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-09-13 10:45:37
 */
public interface UserService extends IService<UserEntity>{

	List<UserEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void deleteBatch(Long[] userIds);

	/**
	 * 根据用户地址查询用户id
	 * @param address
	 * @return
	 */
	Long queryUserIdByAddress(String address);
}
