package moac.ipfs.modules.back.storagePackage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import moac.ipfs.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.storagePackage.dao.StoragePackageDao;
import moac.ipfs.modules.back.storagePackage.entity.StoragePackageEntity;
import moac.ipfs.modules.back.storagePackage.service.StoragePackageService;

@Service("storagePackageService")
public class StoragePackageServiceImpl extends ServiceImpl<StoragePackageDao, StoragePackageEntity> implements StoragePackageService {
	@Autowired
	private StoragePackageDao storagePackageDao;
	
	@Override
	public List<StoragePackageEntity> queryList(Map<String, Object> map){
		return storagePackageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return storagePackageDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] storagePackageIds){
		storagePackageDao.deleteBatch(storagePackageIds);
	}

	@Override
	public void queryUpdatePackage() {
		List<StoragePackageEntity> list = storagePackageDao.queryUpdatePackage();
		Date now = new Date();
		String nowStr = DateUtils.format(now);
		String lastMonthStr = DateUtils.format(DateUtils.addDateMonths(now,-1));
		for (int i = 0; i < list.size(); i++) {
			String expireDate = DateUtils.timestampToDate(list.get(i).getExpireDate(),DateUtils.DATE_PATTERN);
			if (expireDate.compareTo(lastMonthStr) <= 0){
				list.get(i).setStatus(2);
			}
			else if (expireDate.compareTo(lastMonthStr) > 0 && expireDate.compareTo(nowStr) <= 0){
				list.get(i).setStatus(1);
			}else if (expireDate.compareTo(nowStr) > 0){
				continue;
			}
			this.updateById(list.get(i));
		}
	}

	@Override
	public void updatePackageBuyStatus() {
		List<StoragePackageEntity> list = this.selectList(new EntityWrapper<StoragePackageEntity>()
				.eq("buy_status",0));
		for (StoragePackageEntity entity:list) {

		}
	}

}
