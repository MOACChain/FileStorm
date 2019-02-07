package moac.ipfs.modules.back.subchain.service;

import moac.ipfs.modules.back.subchain.entity.SubchainEntity;
import com.baomidou.mybatisplus.service.IService;
import moac.ipfs.modules.back.sys.entity.SysUserEntity;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-02 18:10:35
 */
public interface SubchainService extends IService<SubchainEntity>{
	
	List<SubchainEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

	/**
	 * 新增存储子链
	 * @param subchain
	 */
	void add(SubchainEntity subchain,SysUserEntity sysUserEntity);

	/**
	 * 删除存储子链
	 * @param subchain
	 */
	void delete(SubchainEntity subchain,SysUserEntity sysUserEntity);

	/**
	 * 查询符合新增文件条件的子链合约地址
	 * @param map
	 * @return
	 */
	SubchainEntity queryAddFileList(Map<String, Object> map);

	/**
	 * 同步所有存储子链的数据
	 * @param sysUserEntity
	 */
    void synService(SysUserEntity sysUserEntity);

	List<String> querySubList();
}
