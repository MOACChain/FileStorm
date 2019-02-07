package moac.ipfs.common.utils;


import moac.ipfs.common.exception.RRException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author GZC
 * @date 2018/9/6
 * @desc 对参数进行加密
 */
public class SignNetUtils {

    /**
     * 在Post的时候，给Field字段占位
     */
    public final static String EMPTY_HOLDER = "empty_holder";

    public static String getSignParams(Object obj,HttpServletRequest request) throws Exception{

        /**
         * 获取设备号
         */
        //String UDID = request.getHeader("UDID");
        Map<String, String> newMap = objectToMap(obj);
        StringBuilder newParams = new StringBuilder();

        Set<String> keySet = newMap.keySet();
        // 需要排序
        ArrayList<String> keyList = new ArrayList<>();

        for (String key : keySet) {
            if ("sign".equals(key) || "img".equals(key)){
                continue;
            }
            keyList.add(key);
        }
        //keyList.add("UDID");
        Collections.sort(keyList);

        ArrayList<String> valueList = new ArrayList<>();
        for (String key:keyList) {
            if (newMap.get(key) == null || "".equals(newMap.get(key)) || "null".equals(newMap.get(key))){
                continue;
            }
            /*if ("UDID".equals(key)){
                valueList.add(UDID);
            }else {*/
                valueList.add(newMap.get(key));
            //}
        }

        for (String str : valueList) {
            if (!"".equals(str)) {
                newParams.append(str);
                newParams.append("#$");
            }
        }
        newParams.insert(0, "@#lianban$moacipfs@#");
        return SHA1Utils.SHA1(newParams.toString());
    }

    public static Map<String,String> objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), String.valueOf(field.get(obj)));
        }
        return map;
    }

    public static void checkSign(Object obj, String sign, HttpServletRequest request,boolean ignoreSign){
        if (ignoreSign){
            return;
        }
        try {
            String signStr = SignNetUtils.getSignParams(obj,request);
            if (!signStr.equals(sign)){
                throw new RRException("无效的请求!");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RRException("无效的请求!");
        }
    }
}
