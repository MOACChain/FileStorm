package moac.ipfs.modules.back.subchain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.subchain.dao.SubchainLogDao;
import moac.ipfs.modules.back.subchain.entity.SubchainLogEntity;
import moac.ipfs.modules.back.subchain.service.SubchainLogService;

@Service("subchainLogService")
public class SubchainLogServiceImpl extends ServiceImpl<SubchainLogDao, SubchainLogEntity> implements SubchainLogService {
	@Autowired
	private SubchainLogDao subchainLogDao;
	
	@Override
	public List<SubchainLogEntity> queryList(Map<String, Object> map){
		return subchainLogDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return subchainLogDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Integer[] subLogIds){
		subchainLogDao.deleteBatch(subLogIds);
	}
	
}
