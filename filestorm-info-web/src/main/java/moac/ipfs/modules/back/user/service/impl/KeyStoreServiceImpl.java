package moac.ipfs.modules.back.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.user.dao.KeyStoreDao;
import moac.ipfs.modules.back.user.entity.KeyStoreEntity;
import moac.ipfs.modules.back.user.service.KeyStoreService;

@Service("keyStoreService")
public class KeyStoreServiceImpl extends ServiceImpl<KeyStoreDao, KeyStoreEntity> implements KeyStoreService {
	@Autowired
	private KeyStoreDao keyStoreDao;
	
	@Override
	public List<KeyStoreEntity> queryList(Map<String, Object> map){
		return keyStoreDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return keyStoreDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] keyStoreIds){
		keyStoreDao.deleteBatch(keyStoreIds);
	}
	
}
