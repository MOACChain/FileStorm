package moac.ipfs.modules.back.subchain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import moac.ipfs.common.exception.RRException;
import moac.ipfs.common.httputils.OkHttpClients;
import moac.ipfs.common.httputils.OkHttpParam;
import moac.ipfs.common.httputils.OkhttpResult;
import moac.ipfs.common.utils.Constant;
import moac.ipfs.common.utils.Utils;
import moac.ipfs.modules.back.subchain.entity.SubchainLogEntity;
import moac.ipfs.modules.back.subchain.service.SubchainLogService;
import moac.ipfs.modules.back.sys.entity.SysUserEntity;
import moac.ipfs.modules.back.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import moac.ipfs.modules.back.subchain.dao.SubchainDao;
import moac.ipfs.modules.back.subchain.entity.SubchainEntity;
import moac.ipfs.modules.back.subchain.service.SubchainService;
import org.springframework.transaction.annotation.Transactional;


@Service("subchainService")
public class SubchainServiceImpl extends ServiceImpl<SubchainDao, SubchainEntity> implements SubchainService {
	@Autowired
	private SubchainDao subchainDao;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SubchainLogService subchainLogService;
	
	@Override
	public List<SubchainEntity> queryList(Map<String, Object> map){
		return subchainDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return subchainDao.queryTotal(map);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(SubchainEntity subchain,SysUserEntity sysUserEntity) {
		SubchainEntity subchainEntity = this.selectOne(new EntityWrapper<SubchainEntity>().eq("subchain_address",subchain.getSubchainAddress()));
		if (subchainEntity != null){
			throw new RRException("已存在此存储子链！");
		}
		Long subchainSize = Utils.formatFileSize(subchain.getStorageSpecification());
		subchain.setSysAddress(sysUserEntity.getUsername());
		OkHttpParam okHttpParam = new OkHttpParam();
		okHttpParam.setApiUrl(Constant.ADDIPFS_URL);
		Map<String, String> map = new HashMap<>(16);
		map.put("subchainAddress", subchain.getSubchainAddress());
		map.put("subchainSize", String.valueOf(subchainSize));
		map.put("password",sysConfigService.getValue("password"));
		map.put("keyStore",sysConfigService.getValue("keyStore"));
		map.put("address",sysConfigService.getValue("address"));
		OkhttpResult result = null;
		JSONObject json = null;
		try {
			result = OkHttpClients.post(okHttpParam, map, String.class, OkHttpClients.SYNCHRONIZE);
			json = JSON.parseObject(result.getResult());
			if (!"success".equals(json.getString("message"))) {
				throw new RRException("新增存储子链错误!");
			}
			JSONObject jsonObject = json.getJSONObject("resultData");
			subchain.setSubchainSize(new BigDecimal(subchainSize));
			this.insert(subchain);
			/**
			 * 增加平台总存储空间
			 */
			String totalStorageSpace = sysConfigService.getValue("totalStorageSpace");
			totalStorageSpace =  String.valueOf(Long.valueOf(totalStorageSpace)+subchainSize);
			sysConfigService.updateValueByKey("totalStorageSpace",totalStorageSpace);
			/**
			 * 增加操作日志
			 */
			SubchainLogEntity subchainLogEntity = new SubchainLogEntity();
			subchainLogEntity.setOperationTime(System.currentTimeMillis());
			subchainLogEntity.setOperationType("新增存储子链");
			subchainLogEntity.setSubchainAddress(subchain.getSubchainAddress());
			subchainLogEntity.setSysUserName(sysUserEntity.getUsername());
			subchainLogService.insert(subchainLogEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RRException("新增存储子链错误!");
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(SubchainEntity subchain,SysUserEntity sysUserEntity){
		OkHttpParam okHttpParam = new OkHttpParam();
		okHttpParam.setApiUrl(Constant.DELETEIPFS_URL);
		Map<String, String> map = new HashMap<>(16);
		map.put("subchainAddress", subchain.getSubchainAddress());
		map.put("password",sysConfigService.getValue("password"));
		map.put("keyStore",sysConfigService.getValue("keyStore"));
		map.put("address",sysConfigService.getValue("address"));
		OkhttpResult result = null;
		JSONObject json = null;
		try {
			result = OkHttpClients.post(okHttpParam, map, String.class, OkHttpClients.SYNCHRONIZE);
			json = JSON.parseObject(result.getResult());
			if (!"success".equals(json.getString("message"))) {
				throw new RRException("删除存储子链错误!");
			}
			JSONObject jsonObject = json.getJSONObject("resultData");
			this.deleteById(subchain);
			/**
			 * 增加操作日志
			 */
			SubchainLogEntity subchainLogEntity = new SubchainLogEntity();
			subchainLogEntity.setOperationTime(System.currentTimeMillis());
			subchainLogEntity.setOperationType("删除存储子链");
			subchainLogEntity.setSubchainAddress(subchain.getSubchainAddress());
			subchainLogEntity.setSysUserName(sysUserEntity.getUsername());
			subchainLogService.insert(subchainLogEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RRException("删除存储子链错误!");
		}
	}

	@Override
	public SubchainEntity queryAddFileList(Map<String, Object> map) {
		return subchainDao.queryAddFileList(map);
	}

	@Override
	public void synService(SysUserEntity sysUserEntity) {
		if (!"admin".equals(sysUserEntity.getUsername())){
			throw new RRException("没有权限!");
		}
		OkHttpParam okHttpParam = new OkHttpParam();
		okHttpParam.setApiUrl(Constant.GETALLIPFSINFO_URL);
		Map<String, String> map = new HashMap<>(16);
		OkhttpResult result = null;
		JSONObject json = null;
		try {
			result = OkHttpClients.post(okHttpParam, map, String.class, OkHttpClients.SYNCHRONIZE);
			json = JSON.parseObject(result.getResult());
			if (!"success".equals(json.getString("message"))) {
				throw new RRException("同步存储子链错误!");
			}
			JSONObject jsonObject = json.getJSONObject("resultData");
			List<SubchainEntity> list = JSONObject.parseArray(jsonObject.toJSONString(),SubchainEntity.class);
			for (SubchainEntity subchain:list) {
				this.insert(subchain);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RRException("同步存储子链错误!");
		}
	}

	@Override
	public List<String> querySubList() {
		return subchainDao.querySubList();
	}

}
