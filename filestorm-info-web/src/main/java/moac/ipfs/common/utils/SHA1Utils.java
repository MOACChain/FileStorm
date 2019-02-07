package moac.ipfs.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ellison
 * @date 2017/11/6
 * @desc SHA1加密类
 */

public class SHA1Utils {
    private static Logger loggerDigest = LoggerFactory.getLogger(SHA1Utils.class);

    public static String SHA1(String encrypt) {

        try {
            loggerDigest.info("签名字符串："+encrypt);
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(encrypt.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换成 十六进制数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
