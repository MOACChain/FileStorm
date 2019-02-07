package moac.ipfs.modules.back.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import moac.ipfs.common.exception.RRException;
import moac.ipfs.common.utils.CfgUtils;
import moac.ipfs.common.utils.ErrorCodeConstant;
import moac.ipfs.common.utils.JwtUtils;
import moac.ipfs.modules.back.user.entity.UserEntity;
import moac.ipfs.modules.back.user.dao.UserDao;
import moac.ipfs.modules.back.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private UserDao userDao;


	@Override
	public List<UserEntity> queryList(Map<String, Object> map){
		return userDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return userDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] userIds){
		userDao.deleteBatch(userIds);
	}

	@Override
	public Long queryUserIdByAddress(String address) {
		return userDao.queryUserIdByAddress(address);
	}

}
