package moac.ipfs.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import moac.ipfs.common.exception.RRException;
import moac.ipfs.common.httputils.OkHttpClients;
import moac.ipfs.common.httputils.OkHttpParam;
import moac.ipfs.common.httputils.OkhttpResult;
import moac.ipfs.modules.back.storagePackage.entity.StoragePackageEntity;
import moac.ipfs.modules.back.user.entity.KeyStoreEntity;
import moac.ipfs.modules.back.user.entity.UserEntity;
import moac.ipfs.modules.back.user.service.KeyStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.*;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: GZC
 * @create: 2018-11-23 15:39
 * @description: Moac链操作
 **/
@Component
public class MoacUtils {
    private static Logger logger = LoggerFactory.getLogger(MoacUtils.class);
    @Autowired
    private CfgUtils cfgUtils;
    @Autowired
    private KeyStoreService keyStoreService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 创建用户地址
     * @param userEntity
     * @return
     * @throws Exception
     */
    public UserEntity createAddress(UserEntity userEntity) throws Exception {
        
    }

    /**
     * keyStore导入地址
     * @param keyStore
     * @param password
     * @return
     * @throws Exception
     */
    public Credentials keyStoreImportAddress(String keyStore, String password) throws Exception {
        
    }

    /**
     * 私钥导入地址
     * @param privateKey
     * @param password
     * @return
     * @throws Exception
     */
    public WalletFile PrivateKeyImportAddress(String privateKey, String password) throws Exception {
       
    }


    /**
     * 获取钱包地址名称
     * @param walletFile
     * @return
     */
    private String getWalletFileName(WalletFile walletFile) {
       
    }

    /**
     * 查询主链余额
     * @param address
     * @return
     */
    public BigDecimal queryMainChainBalance(String address){
       
    }

    /**
     * 查询子链余额
     * @param address
     * @return
     */
    public BigDecimal querySubChainBalance(String address){
       
    }

    /**
     * 查询主链块高
     * @param address
     * @return
     */
    public String queryMainChainBlock(){
       
    }

    /**
     * 查询子链块高
     * @param address
     * @return
     */
    public String querySubChainBlock(){
        
    }

    /**
     * 生成.json格式文件
     */
    public String createJsonFile(String jsonString) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("'UTC--'yyyy-MM-dd'T'HH-mm-ss.nVV'--'");
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        String fileName = now.format(format) + ".json";

        // 拼接文件完整路径
        String fileUrl = this.getClass().getClassLoader().getResource("static").getPath() + "/" + fileName;

        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(fileUrl);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();


            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    /**
     * 刪除文件
     *
     * @param fileUrl
     */
    public void delFile(String fileUrl) {
        File file = new File(fileUrl);
        if (file.exists() && file.isFile())
            file.delete();
    }


    // 转化块高
    public static String getBlockNumber(String number) {
        number = number.substring(2, number.length());
        BigInteger big = new BigInteger(number, 16);
        number = new BigDecimal(String.valueOf(big.doubleValue())).toBigInteger().toString();
        return number;
    }

    // 主链转换金额
    public static BigDecimal getBaclanceNumber(String number, int count) {
        number = number.substring(2, number.length());
        BigInteger big = new BigInteger(number, 16);
        number = new BigDecimal(String.valueOf(big.doubleValue() / Math.pow(10, count))).toPlainString();
        return new BigDecimal(number);
    }

    // 子链转换金额
    public static BigDecimal getSubBaclanceNumber(String number, int count) {
        BigInteger big = new BigInteger(number, 10);
        number = new BigDecimal(String.valueOf(big.doubleValue() / Math.pow(10, count))).toPlainString();
        return new BigDecimal(number);
    }

    /**
     * 主链ERC20转账
     * @param formAddress
     * @param toAddress
     * @param password
     * @param keyStore
     * @param amount
     * @return
     */
    public static JSONObject mainChainTrans(String formAddress,String toAddress,String password,String keyStore,String amount){
       
    }

    /**
     * 子链coin转账
     * @param formAddress
     * @param toAddress
     * @param password
     * @param keyStore
     * @param amount
     * @return
     */
    public static JSONObject subChainTrans(String formAddress,String toAddress,String password,String keyStore,String amount){
        
    }

    /**
     * 子链coin兑换fst
     * @param formAddress
     * @param password
     * @param keyStore
     * @param amount
     * @return
     */
    public static JSONObject subChainCoinToFst(String formAddress,String password,String keyStore,String amount){
        
    }

    /**
     * fst兑换子链coin
     * @param formAddress
     * @param password
     * @param keyStore
     * @param amount
     * @return
     */
    public static JSONObject fstToSubChainCoin(String formAddress,String password,String keyStore,String amount){
       
    }
}
