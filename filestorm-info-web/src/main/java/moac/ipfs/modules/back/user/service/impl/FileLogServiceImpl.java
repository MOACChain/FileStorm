package moac.ipfs.modules.back.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.user.dao.FileLogDao;
import moac.ipfs.modules.back.user.entity.FileLogEntity;
import moac.ipfs.modules.back.user.service.FileLogService;

@Service("fileLogService")
public class FileLogServiceImpl extends ServiceImpl<FileLogDao, FileLogEntity> implements FileLogService {
	@Autowired
	private FileLogDao fileLogDao;
	
	@Override
	public List<FileLogEntity> queryList(Map<String, Object> map){
		return fileLogDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return fileLogDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] logIds){
		fileLogDao.deleteBatch(logIds);
	}
	
}
