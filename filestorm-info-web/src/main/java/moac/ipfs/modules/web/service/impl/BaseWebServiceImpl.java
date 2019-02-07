package moac.ipfs.modules.web.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import moac.ipfs.common.exception.RRException;
import moac.ipfs.common.httputils.OkHttpClients;
import moac.ipfs.common.httputils.OkHttpParam;
import moac.ipfs.common.httputils.OkhttpResult;
import moac.ipfs.common.utils.*;
import moac.ipfs.modules.back.currency.entity.CurrencyEntity;
import moac.ipfs.modules.back.currency.entity.CurrencyRecordEntity;
import moac.ipfs.modules.back.currency.service.CurrencyRecordService;
import moac.ipfs.modules.back.currency.service.CurrencyService;
import moac.ipfs.modules.back.storagePackage.entity.StoragePackageEntity;
import moac.ipfs.modules.back.storagePackage.service.StoragePackageService;
import moac.ipfs.modules.back.subchain.dao.SubchainDao;
import moac.ipfs.modules.back.subchain.entity.SubchainEntity;
import moac.ipfs.modules.back.subchain.entity.SubchainIpEntity;
import moac.ipfs.modules.back.subchain.service.SubchainIpService;
import moac.ipfs.modules.back.subchain.service.SubchainService;
import moac.ipfs.modules.back.sys.service.SysConfigService;
import moac.ipfs.modules.back.user.entity.*;
import moac.ipfs.modules.back.user.service.FileLogService;
import moac.ipfs.modules.back.user.service.UserAssetsService;
import moac.ipfs.modules.back.user.service.UserFileService;
import moac.ipfs.modules.back.user.service.UserService;
import moac.ipfs.modules.web.form.*;
import moac.ipfs.modules.back.user.dao.UserDao;
import moac.ipfs.modules.web.service.BaseWebService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


/**
 * @author GZC
 */
@Service("baseWebService")
public class BaseWebServiceImpl extends ServiceImpl<UserDao, UserEntity> implements BaseWebService {
    Logger logger = LoggerFactory.getLogger(BaseWebServiceImpl.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private SubchainService subchainService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private FileLogService fileLogService;
    @Autowired
    private SubchainIpService subchainIpService;
    @Autowired
    private MoacUtils moacUtils;
    @Autowired
    private SubchainDao subchainDao;
    @Autowired
    private StoragePackageService storagePackageService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private UserAssetsService userAssetsService;
    @Autowired
    private CurrencyRecordService currencyRecordService;
    @Autowired
    private CurrencyService currencyService;

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UserEntity importAddressService(ImportAddressForm form, HttpServletRequest request) {
//        OkHttpParam okHttpParam = new OkHttpParam();
//        okHttpParam.setApiUrl(Constant.IMPORT_ADDRESS_URL);
//        Map<String, String> map = new HashMap<>(16);
//        map.put("importType", form.getImportType());
//        map.put("keyStore", form.getKeyStore() == null ? "" : form.getKeyStore());
//        map.put("password", form.getPassword());
//        map.put("mnemonic", form.getMnemonic() == null ? "" : form.getMnemonic());
//        map.put("encryption", form.getEncryption() == null ? "" : form.getEncryption());
//        map.put("plaintextPrivateKey", form.getPlaintextPrivateKey() == null ? "" : form.getPlaintextPrivateKey());
//        OkhttpResult result = null;
//        JSONObject json = null;
//        try {
//            result = OkHttpClients.post(okHttpParam, map, String.class, OkHttpClients.SYNCHRONIZE);
//            json = JSON.parseObject(result.getResult());
//            if (!"success".equals(json.getString("message"))) {
//                logger.error(json.toJSONString());
//                throw new RRException("请求导入地址错误!");
//            }
//            JSONObject jsonObject = json.getJSONObject("resultData");
//            UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>().eq("address", jsonObject.getString("address")));
//            if (userEntity != null) {
//                userEntity.setLastLoginTime(System.currentTimeMillis());
//                userEntity.setLastLoginIp(IPUtils.getIpAddr(request));
//                if (Constant.PLAINTEXTPRIVATEKEY_TYPE.equals(form.getImportType())) {
//                    userEntity.setPassword(form.getPassword());
//                }
//                this.updateById(userEntity);
//            } else {
//                userEntity = new UserEntity();
//                userEntity.setCreateTime(System.currentTimeMillis());
//                userEntity.setLastLoginIp(IPUtils.getIpAddr(request));
//                userEntity.setPassword(form.getPassword());
//                if (StringUtils.isNotBlank(form.getPasswordHint())) {
//                    userEntity.setPasswordHint(form.getPasswordHint());
//                }
//                userEntity.setUserAccount("IPFS" + Utils.getSerialNo());
//                userEntity.setAddress(jsonObject.getString("address"));
//                userEntity.setKeyStore(jsonObject.getString("keyStore"));
//                this.insert(userEntity);
//            }
//            return userEntity;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RRException("请求导入地址错误!");
//        }
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UserEntity createAddressService(CreateAddressForm form, HttpServletRequest request) {
//        OkHttpParam okHttpParam = new OkHttpParam();
//        okHttpParam.setApiUrl(Constant.CREATE_ADDRESS_URL);
//        Map<String, String> map = new HashMap<>(16);
//        map.put("password", form.getPassword());
//        OkhttpResult result = null;
//        JSONObject json = null;
//        try {
//            result = OkHttpClients.post(okHttpParam, map, String.class, OkHttpClients.SYNCHRONIZE);
//            json = JSON.parseObject(result.getResult());
//            if (!"success".equals(json.getString("message"))) {
//                logger.error(json.toJSONString());
//                throw new RRException("请求创建地址错误!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RRException("请求创建地址错误!");
//        }
//        JSONObject jsonObject = json.getJSONObject("resultData");
//        UserEntity userEntity = new UserEntity();
//        userEntity.setCreateTime(System.currentTimeMillis());
//        userEntity.setLastLoginIp(IPUtils.getIpAddr(request));
//        userEntity.setPassword(form.getPassword());
//        userEntity.setLastLoginTime(System.currentTimeMillis());
//        if (StringUtils.isNotBlank(form.getPasswordHint())) {
//            userEntity.setPasswordHint(form.getPasswordHint());
//        }
//        userEntity.setUserAccount("IPFS" + Utils.getSerialNo());
//        userEntity.setAddress(jsonObject.getString("address"));
//        userEntity.setKeyStore(jsonObject.getString("keystore"));
//        this.insert(userEntity);
//        return userEntity;
//    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity importAddressService(ImportAddressForm form, HttpServletRequest request) {
        String address = null;
        String keyStore = null;
        try {
            /**
             * KEYSTORE_TYPE导入
             */
            if (Constant.KEYSTORE_TYPE.equals(form.getImportType())) {
                Credentials credentials = moacUtils.keyStoreImportAddress(form.getKeyStore(), form.getPassword());
                address = credentials.getAddress();
                keyStore = form.getKeyStore();
            }
            /**
             * 私钥导入地址方式
             */
            else if (Constant.PLAINTEXTPRIVATEKEY_TYPE.equals(form.getImportType())) {
                WalletFile walletFile = moacUtils.PrivateKeyImportAddress(form.getPlaintextPrivateKey(), form.getPassword());
                address = "0x" + walletFile.getAddress();
                JSONObject jsonObject = (JSONObject) JSON.toJSON(walletFile);
                keyStore = jsonObject.toJSONString();
            }
            /**
             * 助记词导入地址方式
             */
            else if (Constant.MNEMONIC_TYPE.equals(form.getImportType())) {
                throw new RRException("暂未开放!");
            } else {
                throw new RRException("请求导入地址错误!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("请求导入地址错误!");
        }
        if (StringUtils.isBlank(address) || StringUtils.isBlank(keyStore)) {
            throw new RRException("请求导入地址错误!");
        }
        UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>().eq("address", address));
        if (userEntity != null) {
            userEntity.setLastLoginTime(System.currentTimeMillis());
            userEntity.setLastLoginIp(IPUtils.getIpAddr(request));
            if (Constant.PLAINTEXTPRIVATEKEY_TYPE.equals(form.getImportType())) {
                userEntity.setPassword(form.getPassword());
            }
            this.updateById(userEntity);
        } else {
            userEntity = new UserEntity();
            userEntity.setCreateTime(System.currentTimeMillis());
            userEntity.setLastLoginIp(IPUtils.getIpAddr(request));
            userEntity.setPassword(form.getPassword());
            if (StringUtils.isNotBlank(form.getPasswordHint())) {
                userEntity.setPasswordHint(form.getPasswordHint());
            }
            userEntity.setUserAccount("IPFS" + Utils.getSerialNo());
            userEntity.setAddress(address);
            userEntity.setKeyStore(keyStore);
            this.insert(userEntity);
        }
        /**
         * 创建用户资产
         */
        List<CurrencyEntity> currencyEntityList = currencyService.queryList(null);
        for (CurrencyEntity currencyEntity : currencyEntityList) {
            UserAssetsEntity userAssetsEntity = userAssetsService.selectOne(
                    new EntityWrapper<UserAssetsEntity>()
                            .eq("currency_number", currencyEntity.getNumber())
                            .eq("user_id", userEntity.getUserId()));
            if (userAssetsEntity != null) {
                continue;
            } else {
                userAssetsEntity = new UserAssetsEntity();
                userAssetsEntity.setBalance(new BigDecimal(0));
                userAssetsEntity.setCurrencyNumber(currencyEntity.getNumber());
                userAssetsEntity.setLock(new BigDecimal(0));
                userAssetsEntity.setUserId(userEntity.getUserId());
                userAssetsService.insert(userAssetsEntity);
            }
        }
        /**
         * 创建免费套餐(如果没有)
         */
        StoragePackageEntity storagePackageEntity = storagePackageService.selectOne(new EntityWrapper<StoragePackageEntity>());
        if (storagePackageEntity == null){
            BigDecimal rate = new BigDecimal("1024");
            BigDecimal a1G = new BigDecimal("1").multiply(rate).multiply(rate).multiply(rate);
            storagePackageEntity.setStatus(-1);
            storagePackageEntity.setCreateDate(System.currentTimeMillis());
            storagePackageEntity.setCurrencyNumber("");
            storagePackageEntity.setExpireDate(-1l);
            storagePackageEntity.setFormatSize(Utils.formatFileSize(Long.valueOf(a1G.toEngineeringString())));
            storagePackageEntity.setPrice(new BigDecimal(0));
            storagePackageEntity.setRealSize(a1G);
            storagePackageEntity.setUseFormatSize("0");
            storagePackageEntity.setUserId(userEntity.getUserId());
            storagePackageEntity.setUserRealSize(new BigDecimal(0));
            storagePackageService.insert(storagePackageEntity);
        }
        return userEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity createAddressService(CreateAddressForm form, HttpServletRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setCreateTime(System.currentTimeMillis());
        userEntity.setLastLoginIp(IPUtils.getIpAddr(request));
        userEntity.setPassword(form.getPassword());
        userEntity.setLastLoginTime(System.currentTimeMillis());
        if (StringUtils.isNotBlank(form.getPasswordHint())) {
            userEntity.setPasswordHint(form.getPasswordHint());
        }
        userEntity.setUserAccount("IPFS" + Utils.getSerialNo());
        this.insert(userEntity);
        try {
            userEntity = moacUtils.createAddress(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("请求创建地址错误!");
        }
        this.updateById(userEntity);
        /**
         * 创建用户资产
         */
        List<CurrencyEntity> currencyEntityList = currencyService.queryList(null);
        for (CurrencyEntity currencyEntity : currencyEntityList) {
            UserAssetsEntity userAssetsEntity = userAssetsService.selectOne(
                    new EntityWrapper<UserAssetsEntity>()
                            .eq("currency_number", currencyEntity.getNumber())
                            .eq("user_id", userEntity.getUserId()));
            if (userAssetsEntity != null) {
                continue;
            } else {
                userAssetsEntity = new UserAssetsEntity();
                userAssetsEntity.setBalance(new BigDecimal(0));
                userAssetsEntity.setCurrencyNumber(currencyEntity.getNumber());
                userAssetsEntity.setLock(new BigDecimal(0));
                userAssetsEntity.setUserId(userEntity.getUserId());
                userAssetsService.insert(userAssetsEntity);
            }
        }
        /**
         * 创建免费套餐(如果没有)
         */
        StoragePackageEntity storagePackageEntity = storagePackageService.selectOne(new EntityWrapper<StoragePackageEntity>());
        if (storagePackageEntity == null){
            BigDecimal rate = new BigDecimal("1024");
            BigDecimal a1G = new BigDecimal("1").multiply(rate).multiply(rate).multiply(rate);
            storagePackageEntity.setStatus(-1);
            storagePackageEntity.setCreateDate(System.currentTimeMillis());
            storagePackageEntity.setCurrencyNumber("");
            storagePackageEntity.setExpireDate(-1l);
            storagePackageEntity.setFormatSize(Utils.formatFileSize(Long.valueOf(a1G.toEngineeringString())));
            storagePackageEntity.setPrice(new BigDecimal(0));
            storagePackageEntity.setRealSize(a1G);
            storagePackageEntity.setUseFormatSize("0");
            storagePackageEntity.setUserId(userEntity.getUserId());
            storagePackageEntity.setUserRealSize(new BigDecimal(0));
            storagePackageService.insert(storagePackageEntity);
        }
        return userEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFileService(FileParamsForm form) {
        StoragePackageEntity storagePackageEntity = storagePackageService.selectOne(
                new EntityWrapper<StoragePackageEntity>().eq("storage_package_id",form.getStoragePackageId()));
        if (storagePackageEntity == null) {
            throw new RRException("套餐不存在！");
        }
        if (new BigDecimal(form.getFileRealSize()).compareTo
                (storagePackageEntity.getRealSize().subtract(storagePackageEntity.getUserRealSize())) > 0) {
            throw new RRException("套餐存储空间不足！");
        }

        UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>().eq("address", form.getAddress()));
        UserFileEntity userFileEntity = userFileService.selectOne(new EntityWrapper<UserFileEntity>().eq("hash", form.getHash()).eq("user_id", userEntity.getUserId()));
        if (userFileEntity != null) {
            throw new RRException("文件已存在");
        }
        OkHttpParam okHttpParam = new OkHttpParam();
        okHttpParam.setApiUrl(Constant.SAVEFILE_URL);
        Map<String, String> map = new HashMap<>(16);
        map.put("address", form.getAddress());
        map.put("filePath", form.getFilePath());
        map.put("fileName", form.getFileName());
        map.put("fileRealSize", form.getFileRealSize());
        map.put("password", userEntity.getPassword());
        map.put("keyStore", userEntity.getKeyStore());
        /**
         * 新增文件表记录
         */
        userFileEntity = new UserFileEntity();
        userFileEntity.setCreateTime(System.currentTimeMillis());
        userFileEntity.setFileRealSize(new BigDecimal(form.getFileRealSize()));
        userFileEntity.setFileSize(Utils.formatFileSize(Long.valueOf(form.getFileRealSize())));
        userFileEntity.setRemark(form.getRemark());
        userFileEntity.setUserId(userService.queryUserIdByAddress(form.getAddress()));
        userFileEntity.setFileName(form.getFileName());
        userFileEntity.setEncrypt(form.getEncrypt());
        userFileService.insert(userFileEntity);
        map.put("createTime", String.valueOf(userFileEntity.getCreateTime()));
        map.put("encrypt", String.valueOf(form.getEncrypt()));
        map.put("fileId", String.valueOf(userFileEntity.getFileId()));
        SubchainEntity subchainEntity = selectSubchainAddress(form.getFileRealSize());
        /**
         * 更新存储子链信息
         */
        subchainEntity.setRemainSize(subchainEntity.getRemainSize().subtract(new BigDecimal(form.getFileRealSize())));
        subchainEntity.setUseSize(subchainEntity.getUseSize().add(new BigDecimal(form.getFileRealSize())));
        subchainEntity.setPercentageUse(subchainEntity.getUseSize().divide(subchainEntity.getSubchainSize(), 6, BigDecimal.ROUND_HALF_UP));
        subchainService.updateById(subchainEntity);
        map.put("subchainAddress", subchainEntity.getSubchainAddress());
        map.put("subchainSize", subchainEntity.getSubchainSize().toPlainString());
        map.put("remainSize", subchainEntity.getRemainSize().toPlainString());
        BigDecimal percentageUse = subchainEntity.getPercentageUse().multiply(new BigDecimal("1000000"));
        map.put("percentageUse", percentageUse.stripTrailingZeros().toPlainString());
        map.put("sip", findSubchainIp(subchainEntity.getSubchainAddress()));
        OkhttpResult result = null;
        JSONObject json = null;
        try {
            result = OkHttpClients.post(okHttpParam, map, String.class, OkHttpClients.SYNCHRONIZE);
            json = JSON.parseObject(result.getResult());
            JSONObject jsonObject = json.getJSONObject("resultData");
            if (!"success".equals(json.getString("message"))) {
                logger.error(json.toJSONString());
                throw new RRException("新增文件错误!");
            }
            userFileEntity.setHash(jsonObject.getString("hash"));
            userFileEntity.setSubchainAddress(subchainEntity.getSubchainAddress());
            userFileService.updateById(userFileEntity);
            /**
             * 新增文件操作日志
             */
            doFileLog(userFileEntity, form.getAddress(), "上传文件");

            /**
             * 修改所选套餐存储空间
             */
            storagePackageEntity.setUserRealSize(storagePackageEntity.getUserRealSize().add(new BigDecimal(form.getFileRealSize())));
            storagePackageEntity.setUseFormatSize(Utils.formatFileSize(Long.valueOf(storagePackageEntity.getUserRealSize().toPlainString())));
            storagePackageService.updateById(storagePackageEntity);
            /**
             * 增加平台总存储空间
             */
            String totalUseStorageSpace = sysConfigService.getValue("totalUseStorageSpace");
            totalUseStorageSpace = String.valueOf(Long.valueOf(totalUseStorageSpace) + form.getFileRealSize());
            sysConfigService.updateValueByKey("totalUseStorageSpace", totalUseStorageSpace);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("新增文件错误!");
        }
    }

    @Override
    public Map<String, Object> queryHomeDataService(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>(16);
        data.put("totalStorageSpace", sysConfigService.getValue("totalStorageSpace"));
        data.put("totalMiners", sysConfigService.getValue("totalMiners"));
        data.put("totalUseStorageSpace", sysConfigService.getValue("totalUseStorageSpace"));
        data.put("fstTotalMarket", sysConfigService.getValue("fstTotalMarket"));
        data.put("mainChainBlockHeight", moacUtils.queryMainChainBlock());
        data.put("subChainBlockHeight", moacUtils.querySubChainBlock());
        data.put("refreshTime", "30s");
        return data;
    }

    @Override
    public String queryPrivateKeyService(ImportAddressForm form) {
        try {
            Credentials credentials = moacUtils.keyStoreImportAddress(form.getKeyStore(), form.getPassword());
            return credentials.getEcKeyPair().getPrivateKey().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("查看私钥失败!");
        }
    }

    @Override
    public List<StoragePackageEntity> queryPackageListService(StoragePackageParamsFrom form) {
        List<StoragePackageEntity> list = new ArrayList<>();
        if (form.getStatus() == 3) {
            if (form.getBuyStatus() == 3){
                list = storagePackageService.selectList(
                        new EntityWrapper<StoragePackageEntity>()
                                .eq("user_id", form.getUserId()));
            }else {
                list = storagePackageService.selectList(
                        new EntityWrapper<StoragePackageEntity>()
                                .eq("user_id", form.getUserId())
                                .eq("buy_status", form.getBuyStatus()));
            }
        } else {
            if (form.getBuyStatus() == 3){
                list = storagePackageService.selectList(
                        new EntityWrapper<StoragePackageEntity>()
                                .eq("user_id", form.getUserId())
                                .eq("status", form.getStatus()));
            }else {
                list = storagePackageService.selectList(
                        new EntityWrapper<StoragePackageEntity>()
                                .eq("user_id", form.getUserId())
                                .eq("status", form.getStatus())
                                .eq("buy_status", form.getBuyStatus()));
            }
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void subChainTransService(StoragePackageParamsFrom form) {
        UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>()
                .eq("address", form.getAddress()));
        if (!userEntity.getPassword().equals(form.getPassword())) {
            throw new RRException("密码不正确！");
        }
        BigDecimal balance = moacUtils.querySubChainBalance(userEntity.getAddress());
        if (balance.compareTo(form.getPrice()) < 0) {
            throw new RRException("COIN余额不足！");
        }
        JSONObject json = moacUtils.subChainTrans(userEntity.getAddress(), sysConfigService.getValue("testSubChainAddres"), userEntity.getPassword(), userEntity.getKeyStore(), form.getPrice().toPlainString());
        JSONObject jsonObject = json.getJSONObject("resultData");
        StoragePackageEntity storagePackageEntity = new StoragePackageEntity();
        storagePackageEntity.setBuyHash(jsonObject.getString("hash"));
        storagePackageEntity.setUseFormatSize("0");
        storagePackageEntity.setUserRealSize(new BigDecimal(0));
        storagePackageEntity.setRealSize(new BigDecimal(Utils.formatFileSize(form.getStorageSize())));
        storagePackageEntity.setUserId(userEntity.getUserId());
        storagePackageEntity.setPrice(form.getPrice());
        storagePackageEntity.setFormatSize(form.getStorageSize());
        storagePackageEntity.setCurrencyNumber("COIN");
        storagePackageEntity.setCreateDate(System.currentTimeMillis());
        storagePackageEntity.setStatus(0);
        Date expireDate = DateUtils.getNextMonthFirstDay();
        expireDate = DateUtils.addDateMonths(expireDate, form.getMonthNumber() - 1);
        storagePackageEntity.setExpireDate(expireDate.getTime());
        storagePackageEntity.setBuyStatus(1);
        storagePackageService.insert(storagePackageEntity);
    }

    @Override
    public UserAssetsEntity queryBalanceService(AssetsFormEntity form) {
        return currencyService.queryBalanceService(form);
    }

    @Override
    public List<UserAssetsEntity> queryAssetsListService(AssetsFormEntity form) {
        UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>().eq("address", form.getAddress()));
        List<UserAssetsEntity> list = userAssetsService.selectList(new EntityWrapper<UserAssetsEntity>()
                .eq("user_id", userEntity.getUserId()));
        for (int i = 0; i < list.size(); i++) {
            if ("FST".equals(list.get(i).getCurrencyNumber())) {
                BigDecimal balance = moacUtils.queryMainChainBalance(userEntity.getAddress());
                list.get(i).setBalance(balance);
            } else if ("COIN".equals(list.get(i).getCurrencyNumber())) {
                BigDecimal balance = moacUtils.querySubChainBalance(userEntity.getAddress());
                list.get(i).setBalance(balance);
            } else {
                continue;
            }
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fstToSubChainCoinService(AssetsFormEntity form) {
        currencyService.fstToSubChainCoinService(form);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void subChainCoinToFstService(AssetsFormEntity form) {
        currencyService.subChainCoinToFstService(form);
    }

    @Override
    public String queryFileByHashService(FileParamsForm form) {
        OkHttpParam okHttpParam = new OkHttpParam();
        okHttpParam.setApiUrl(Constant.READFILE_URL);
        Map<String, String> map = new HashMap<>(16);
        map.put("address", form.getAddress());
        map.put("hash", form.getHash());
        UserFileEntity userFileEntity = userFileService.selectOne(new EntityWrapper<UserFileEntity>().eq("hash", form.getHash()));
        map.put("subchainAddress", userFileEntity.getSubchainAddress());
        map.put("fileName", userFileEntity.getFileName());
        map.put("encrypt", String.valueOf(userFileEntity.getEncrypt()));
        UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>().eq("address", form.getAddress()));
        map.put("password", userEntity.getPassword());
        map.put("keyStore", userEntity.getKeyStore());
        map.put("sip", findSubchainIp(userFileEntity.getSubchainAddress()));
        OkhttpResult result = null;
        JSONObject json = null;
        try {
            result = OkHttpClients.post(okHttpParam, map, String.class, OkHttpClients.SYNCHRONIZE);
            json = JSON.parseObject(result.getResult());
            if (!"success".equals(json.getString("message"))) {
                logger.error(json.toJSONString());
                throw new RRException("查询文件错误!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("查询文件错误!");
        }
        JSONObject jsonObject = json.getJSONObject("resultData");
        return jsonObject.getString("fileUrl");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFileService(FileParamsForm form) {
        UserFileEntity userFileEntity = userFileService.selectOne(new EntityWrapper<UserFileEntity>().eq("hash", form.getHash()));
        if (userFileEntity.getDeleteType() == 1) {
            throw new RRException("文件已删除!");
        }
        OkHttpParam okHttpParam = new OkHttpParam();
        okHttpParam.setApiUrl(Constant.REMOVEFILE_URL);
        Map<String, String> map = new HashMap<>(16);
        map.put("address", form.getAddress());
        map.put("hash", form.getHash());
        map.put("subchainAddress", userFileEntity.getSubchainAddress());
        map.put("fileRealSize", userFileEntity.getFileRealSize().toPlainString());
        map.put("fileId", String.valueOf(userFileEntity.getFileId()));
        map.put("createTime", String.valueOf(userFileEntity.getCreateTime()));
        map.put("fileName", userFileEntity.getFileName());
        UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>().eq("address", form.getAddress()));
        map.put("password", userEntity.getPassword());
        map.put("keyStore", userEntity.getKeyStore());
        map.put("sip", findSubchainIp(userFileEntity.getSubchainAddress()));
        /**
         * 更新存储子链信息
         */
        SubchainEntity subchainEntity = subchainService.selectOne(new EntityWrapper<SubchainEntity>().eq("subchain_address", userFileEntity.getSubchainAddress()));
        subchainEntity.setRemainSize(subchainEntity.getRemainSize().add(userFileEntity.getFileRealSize()));
        subchainEntity.setUseSize(subchainEntity.getUseSize().subtract(userFileEntity.getFileRealSize()));
        subchainEntity.setPercentageUse(subchainEntity.getUseSize().divide(subchainEntity.getSubchainSize(), 6, BigDecimal.ROUND_HALF_UP));
        subchainService.updateById(subchainEntity);
        map.put("subchainAddress", subchainEntity.getSubchainAddress());
        map.put("subchainSize", subchainEntity.getSubchainSize().toPlainString());
        map.put("remainSize", subchainEntity.getRemainSize().toPlainString());
        BigDecimal percentageUse = subchainEntity.getPercentageUse().multiply(new BigDecimal("1000000"));
        map.put("percentageUse", percentageUse.stripTrailingZeros().toPlainString());
        OkhttpResult result = null;
        JSONObject json = null;
        try {
            result = OkHttpClients.post(okHttpParam, map, String.class, OkHttpClients.SYNCHRONIZE);
            json = JSON.parseObject(result.getResult());
            if ("success".equals(json.getString("message"))) {
                /**
                 * 更新文件逻辑删除
                 */
                userFileEntity.setDeleteType(1);
                userFileService.updateById(userFileEntity);
                /**
                 * 新增文件操作日志
                 */
                doFileLog(userFileEntity, form.getAddress(), "删除文件");
            } else {
                logger.error(json.toJSONString());
                throw new RRException("请求删除文件错误!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("请求删除文件错误!");
        }
    }

    //TODO 打包注意
    public SubchainEntity selectSubchainAddress(String fileSize) {
//        List<String> recentlyUsedList = JSON.parseArray(redisUtils.get("recentlyUsed"), String.class);
        List<String> recentlyUsedList = subchainService.querySubList();
        Map<String, Object> params = new HashMap<>(16);
        if (recentlyUsedList != null && recentlyUsedList.size() > 0) {
//            params.put("list", recentlyUsedList);
        }
        params.put("fileSize", fileSize);
        SubchainEntity subchainAddress = subchainService.queryAddFileList(params);
//        recentlyUsedList.add(subchainAddress.getSubchainAddress());
        subchainDao.insertSubList(subchainAddress.getSubchainAddress());
        if (recentlyUsedList.size() > 10) {
//            recentlyUsedList.remove(0);
            subchainDao.deleteSubList(recentlyUsedList.get(0));
        }
//        String str = JSON.toJSONString(recentlyUsedList);
//        redisUtils.set("recentlyUsed", str, -1);
        return subchainAddress;
    }

    /**
     * 新增文件操作日志
     *
     * @param userFileEntity
     * @param address
     * @param operationType
     */
    private void doFileLog(UserFileEntity userFileEntity, String address, String operationType) {
        FileLogEntity fileLogEntity = new FileLogEntity();
        fileLogEntity.setFileHash(userFileEntity.getHash());
        fileLogEntity.setFileRemark(userFileEntity.getRemark());
        fileLogEntity.setOperationTime(System.currentTimeMillis());
        fileLogEntity.setOperationType(operationType);
        fileLogEntity.setUserAddress(address);
        fileLogEntity.setUserId(userFileEntity.getUserId());
        fileLogService.insert(fileLogEntity);
    }

    /**
     * 随机获取存储子链节点ip
     *
     * @param subchainAddress
     * @return
     */
    public String findSubchainIp(String subchainAddress) {
        List<SubchainIpEntity> list = subchainIpService.selectList(new EntityWrapper<SubchainIpEntity>().eq("subchain_address", subchainAddress));
        Random random = new Random();
        int ran = random.nextInt(list.size());
        SubchainIpEntity subchainIpEntity = list.get(ran);
        return subchainIpEntity.getSip();
    }
}