package moac.ipfs.modules.back.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.user.dao.AssetsChangeDao;
import moac.ipfs.modules.back.user.entity.AssetsChangeEntity;
import moac.ipfs.modules.back.user.service.AssetsChangeService;

@Service("assetsChangeService")
public class AssetsChangeServiceImpl extends ServiceImpl<AssetsChangeDao, AssetsChangeEntity> implements AssetsChangeService {
	@Autowired
	private AssetsChangeDao assetsChangeDao;
	
	@Override
	public List<AssetsChangeEntity> queryList(Map<String, Object> map){
		return assetsChangeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return assetsChangeDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] assetsChangeIds){
		assetsChangeDao.deleteBatch(assetsChangeIds);
	}
	
}
