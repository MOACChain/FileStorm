package moac.ipfs.modules.back.gain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.gain.dao.DividendDao;
import moac.ipfs.modules.back.gain.entity.DividendEntity;
import moac.ipfs.modules.back.gain.service.DividendService;

@Service("dividendService")
public class DividendServiceImpl extends ServiceImpl<DividendDao, DividendEntity> implements DividendService {
	@Autowired
	private DividendDao dividendDao;
	
	@Override
	public List<DividendEntity> queryList(Map<String, Object> map){
		return dividendDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return dividendDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] dividendIds){
		dividendDao.deleteBatch(dividendIds);
	}
	
}
