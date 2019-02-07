package moac.ipfs.modules.web.service;


import com.baomidou.mybatisplus.service.IService;
import moac.ipfs.modules.back.storagePackage.entity.StoragePackageEntity;
import moac.ipfs.modules.back.user.entity.AssetsFormEntity;
import moac.ipfs.modules.back.user.entity.UserAssetsEntity;
import moac.ipfs.modules.back.user.entity.UserEntity;
import moac.ipfs.modules.web.form.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2017-03-23 15:22:06
 */
public interface BaseWebService extends IService<UserEntity> {

	/**
	 * 导入钱包
	 * @param importAddressForm
	 * @param request
	 * @return
	 */
	 UserEntity importAddressService(ImportAddressForm importAddressForm,HttpServletRequest request);


	/**
	 * 创建地址
	 * @param form
	 * @param request
	 * @return
	 */
	UserEntity createAddressService(CreateAddressForm form, HttpServletRequest request);


	/**
	 * 查看文件
	 * @param form
	 * @return
	 */
	String queryFileByHashService(FileParamsForm form);

	/**
	 * 删除文件
	 * @param form
	 * @return
	 */
	void deleteFileService(FileParamsForm form);

	/**
	 * 新增文件
	 * @param form
	 */
	void addFileService(FileParamsForm form);

	/**
	 * 查询首页数据
	 * @param request
	 * @return
	 */
	Map<String, Object> queryHomeDataService(HttpServletRequest request);

	/**
	 * 查看私钥
	 * @param form
	 * @return
	 */
	String queryPrivateKeyService(ImportAddressForm form);

	/**
	 * 查询用户套餐列表
	 * @param form
	 * @return
	 */
	List<StoragePackageEntity> queryPackageListService(StoragePackageParamsFrom form);

	/**
	 * 用户购买套餐
	 * @param from
	 */
	void subChainTransService(StoragePackageParamsFrom from);

	/**
	 * 用户查询余额
	 * @param form
	 * @return
	 */
	UserAssetsEntity queryBalanceService(AssetsFormEntity form);

	/**
	 * 查询用户查询资产列表
	 * @param form
	 * @return
	 */
	List<UserAssetsEntity> queryAssetsListService(AssetsFormEntity form);

	/**
	 * fst兑换子链coin
	 * @param assetsForm
	 */
	void fstToSubChainCoinService(AssetsFormEntity assetsForm);

	/**
	 * 子链coin兑换fst
	 * @param assetsForm
	 */
	void subChainCoinToFstService(AssetsFormEntity assetsForm);
}
