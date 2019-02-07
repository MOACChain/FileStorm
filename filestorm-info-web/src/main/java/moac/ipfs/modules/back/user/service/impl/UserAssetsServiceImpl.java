package moac.ipfs.modules.back.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.user.dao.UserAssetsDao;
import moac.ipfs.modules.back.user.entity.UserAssetsEntity;
import moac.ipfs.modules.back.user.service.UserAssetsService;

@Service("userAssetsService")
public class UserAssetsServiceImpl extends ServiceImpl<UserAssetsDao, UserAssetsEntity> implements UserAssetsService {
	@Autowired
	private UserAssetsDao userAssetsDao;
	
	@Override
	public List<UserAssetsEntity> queryList(Map<String, Object> map){
		return userAssetsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userAssetsDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] assetsIds){
		userAssetsDao.deleteBatch(assetsIds);
	}
	
}
