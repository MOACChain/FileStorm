package moac.ipfs.common.utils;

import com.alibaba.fastjson.JSONObject;
import moac.ipfs.common.exception.RRException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取钱包地址工具类
 * Created by GZC on 2018/4/17.
 */
@Component
public class MoacJsonRpc {
    static Logger log = LoggerFactory.getLogger(MoacJsonRpc.class);

    private static final String MAIN_CHAIN_URL = "";
    private static final String SUB_CHAIN_URL = "";

    /**
     * 生产
     */
//    private static final String MAIN_CHAIN_ADDRESS = "";
//    private static final String SUB_CHAIN_ADDRESS = "";
//    private static final Integer NET_WORK_TYPE = 101;
    /**
     * 测试
     */
    private static final String MAIN_CHAIN_ADDRESS = "";
    private static final String SUB_CHAIN_ADDRESS = "";
    private static final Integer NET_WORK_TYPE = 101;


    /**
     * 查询主链ERC20余额
     * @return
     */
    public static String queryMainChainBalance(String addrss){
       
    }

    /**
     * 查询子链coin余额
     * @return
     */
    public static String querySubChainBalance(String addrss){
       
    }

    /**
     * 查询主链块高
     * @return
     */
    public static String queryMainChainBlock(){
       
    }

    /**
     * 查询子链块高
     * @return
     */
    public static String querySubChainBlock(){
       
    }

    //post请求
    public static String post(String url,JSONObject json) {
//        log.info(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        String result = "";
        try {
            if(json != null){
                StringEntity s = new StringEntity(json.toString(), "utf-8");
//                log.info(json.toJSONString());
                s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                        "application/json"));
                post.setEntity(s);
            }
            // 发送请求
            HttpResponse httpResponse = httpclient.execute(post);
            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line);
            inStream.close();
            result = strber.toString();
        } catch (Exception e) {
            log.info("请求异常");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

}
