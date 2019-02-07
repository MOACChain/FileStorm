package moac.ipfs.common.utils;

import net.sf.json.JSONObject;
import moac.ipfs.common.exception.RRException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取钱包地址工具类
 * @author  by GZC on 2018/4/17.
 */
public class GetNewAddress {
    static Logger log = LoggerFactory.getLogger(GetNewAddress.class);

    private static final String ETHURL = "http://120.79.192.85:9876";

    //获取一个ETH新地址
    public static String getEthAddress(){
        String result = null;
        Object[] str = {"moacipfs"};
        JSONObject json = new JSONObject();
        json.put("method", "personal_newAccount");
        json.put("params", str);
        json.put("id", Object.class);
        try {
            result = Utils.sendPost(ETHURL,json);
            org.json.JSONObject json_result = new org.json.JSONObject(result);
            if(json_result.getString("result") != null){
                result = json_result.getString("result");
            }else{
                throw new RRException("获取ETH新地址失败!",ErrorCodeConstant.FAILED_TO_GET_CURRENCY_ADDRESS);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //获取一个MOAC新地址
    public static String getMoacAddress(){
        //TODO 未完成
        return "ASDIJHASDHAISDIAHDIAD"+Utils.getSerialNo();
    }


}
