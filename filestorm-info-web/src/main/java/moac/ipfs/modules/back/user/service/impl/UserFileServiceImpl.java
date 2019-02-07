package moac.ipfs.modules.back.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.user.dao.UserFileDao;
import moac.ipfs.modules.back.user.entity.UserFileEntity;
import moac.ipfs.modules.back.user.service.UserFileService;

@Service("userFileService")
public class UserFileServiceImpl extends ServiceImpl<UserFileDao, UserFileEntity> implements UserFileService {
	@Autowired
	private UserFileDao userFileDao;
	
	@Override
	public List<UserFileEntity> queryList(Map<String, Object> map){
		return userFileDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userFileDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] fileIds){
		userFileDao.deleteBatch(fileIds);
	}
	
}
