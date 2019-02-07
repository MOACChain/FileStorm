package moac.ipfs.common.httputils;

import com.alibaba.fastjson.JSON;
import moac.ipfs.common.exception.RRException;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: GZC
 * @create: 2018-11-01 15:01
 * @description: 请求工具类
 **/
public class OkHttpClients {
    public static Logger logger = LoggerFactory.getLogger(OkHttpClients.class);

    public static final String SYNCHRONIZE = "synchronize";
    public static final String ASYNCHRONOUS = "asynchronous";

    /**
     * get请求
     */
    public static <T> OkhttpResult<T> get(OkHttpParam restParam, Class<T> tClass,String type) throws Exception {
        String url = restParam.getApiUrl();

        if (StringUtils.isNotBlank(restParam.getApiPath())) {
            url = url+restParam.getApiPath();
        }
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return exec(restParam, request, tClass,type);
    }

    /**
     * POST请求json数据
     */
    public static <T> OkhttpResult<T> post(OkHttpParam restParam, String reqJsonData, Class<T> tClass,String type) throws Exception {
        String url = restParam.getApiUrl();

        if (StringUtils.isNotBlank(restParam.getApiPath())) {
            url = url+restParam.getApiPath();
        }
        RequestBody body = RequestBody.create(restParam.getMediaType(), reqJsonData);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return exec(restParam, request, tClass,type);
    }

    /**
     * POST请求map数据
     */
    public static <T> OkhttpResult<T> post(OkHttpParam restParam, Map<String, String> params, Class<T> tClass,String type) throws Exception {
        String url = restParam.getApiUrl();

        if (StringUtils.isNotBlank(restParam.getApiPath())) {
            url = url+restParam.getApiPath();
        }
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }

        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return exec(restParam, request, tClass,type);
    }

    /**
     * 返回值封装成对象
     */
    private static <T> OkhttpResult<T> exec(OkHttpParam restParam, Request request, Class<T> tClass,String type) throws Exception {
        OkhttpResult clientResult = null;
        if (SYNCHRONIZE.equals(type)){
             clientResult = SynchronizeExec(restParam, request);
        }else {
            clientResult = asynchronousExec(restParam, request);
        }
        String result = clientResult.getResult();
        int status = clientResult.getStatus();
        T t = null;
        if (status == 200) {
            if (result != null && "".equalsIgnoreCase(result)) {
                t = JSON.parseObject(result, tClass);
            }
        } else {
            try {
                result = JSON.parseObject(result, String.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return new OkhttpResult<>(clientResult.getStatus(), result, t);
    }

    /**
     * 同步执行方法
     */
    private static OkhttpResult SynchronizeExec(OkHttpParam restParam, Request request) throws Exception {
        OkhttpResult result = null;
        okhttp3.OkHttpClient client = null;
        ResponseBody responseBody = null;
        try {
            client = new OkHttpClient.Builder()
                    .connectTimeout(restParam.getConnectTimeout(), TimeUnit.MILLISECONDS)
                    .readTimeout(restParam.getReadTimeout(), TimeUnit.MILLISECONDS)
                    .writeTimeout(restParam.getWriteTimeout(), TimeUnit.MILLISECONDS)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                responseBody = response.body();
                if (responseBody != null) {
                    String responseString = responseBody.string();
                    result = new OkhttpResult<>(response.code(), responseString, null);
                }
            } else {
                throw new Exception(response.message());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
            if (client != null) {
                client.dispatcher().executorService().shutdown();   //清除并关闭线程池
                client.connectionPool().evictAll();                 //清除并关闭连接池
                try {
                    if (client.cache() != null) {
                        client.cache().close();                         //清除cache
                    }
                } catch (IOException e) {
                    throw new Exception(e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 异步执行方法
     */
    private static OkhttpResult asynchronousExec(OkHttpParam restParam, Request request) throws Exception {
        final OkhttpResult[] result = {null};
        okhttp3.OkHttpClient client = null;
        try {
            client = new okhttp3.OkHttpClient();

            client.newBuilder()
                    .connectTimeout(restParam.getConnectTimeout(), TimeUnit.MILLISECONDS)
                    .readTimeout(restParam.getReadTimeout(), TimeUnit.MILLISECONDS)
                    .writeTimeout(restParam.getWriteTimeout(), TimeUnit.MILLISECONDS);

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    logger.error(e.getMessage());
                    System.out.println("Fail");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseBody responseBody = null;
                    if (response.isSuccessful()) {
                        responseBody = response.body();
                        if (responseBody != null) {
                            String responseString = responseBody.string();
                            result[0] = new OkhttpResult<>(response.code(), responseString, null);
                        }
                    } else {
                        throw new RRException(response.message());
                    }
                    if (responseBody != null) {
                        responseBody.close();
                    }
                }
            });
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (client != null) {
                //清除并关闭线程池
                client.dispatcher().executorService().shutdown();
                //清除并关闭连接池
                client.connectionPool().evictAll();
                try {
                    if (client.cache() != null) {
                        //清除cache
                        client.cache().close();
                    }
                } catch (IOException e) {
                    throw new Exception(e.getMessage());
                }
            }
        }
        return result[0];
    }

}
