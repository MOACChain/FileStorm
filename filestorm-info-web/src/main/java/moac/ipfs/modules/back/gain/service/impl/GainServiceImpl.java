package moac.ipfs.modules.back.gain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.gain.dao.GainDao;
import moac.ipfs.modules.back.gain.entity.GainEntity;
import moac.ipfs.modules.back.gain.service.GainService;

@Service("gainService")
public class GainServiceImpl extends ServiceImpl<GainDao, GainEntity> implements GainService {
	@Autowired
	private GainDao gainDao;
	
	@Override
	public List<GainEntity> queryList(Map<String, Object> map){
		return gainDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return gainDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] gainIds){
		gainDao.deleteBatch(gainIds);
	}
	
}
