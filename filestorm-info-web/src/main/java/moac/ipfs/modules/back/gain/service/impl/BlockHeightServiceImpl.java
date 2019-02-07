package moac.ipfs.modules.back.gain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.gain.dao.BlockHeightDao;
import moac.ipfs.modules.back.gain.entity.BlockHeightEntity;
import moac.ipfs.modules.back.gain.service.BlockHeightService;

@Service("blockHeightService")
public class BlockHeightServiceImpl extends ServiceImpl<BlockHeightDao, BlockHeightEntity> implements BlockHeightService {
	@Autowired
	private BlockHeightDao blockHeightDao;
	
	@Override
	public List<BlockHeightEntity> queryList(Map<String, Object> map){
		return blockHeightDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return blockHeightDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] blockHeightIds){
		blockHeightDao.deleteBatch(blockHeightIds);
	}
	
}
