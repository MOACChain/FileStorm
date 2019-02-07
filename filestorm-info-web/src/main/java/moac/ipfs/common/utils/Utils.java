package moac.ipfs.common.utils;

import com.googlecode.jsonrpc4j.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

/**
 * 系统工具类
 * Created by GZC on 2018/4/3.
 */
@Component
public class Utils {
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 返回六位数的随机数
     */
    public static String returnRandomNumber() {
        return String.valueOf(((int) ((Math.random() * 9 + 1) * 100000)));
    }

    /**
     * 返回4位数的随机数
     */
    public static String returnRandomNumberFour() {
        return String.valueOf(((int) ((Math.random() * 9 + 1) * 1000)));
    }

    /**
     * 返回6位数的随机数
     */
    public static String returnRandomNumberSix() {
        return String.valueOf(((int) ((Math.random() * 9 + 1) * 100000)));
    }


    /**
     * 获取流水号
     *
     * @return
     */
    public static String getSerialNo() {
        String serialNo = DateUtils.format(new Date()) + "" + String.valueOf(((int) ((Math.random() * 9 + 1) * 100000)));
        return serialNo.replaceAll("-", "");
    }

    /**
     * get请求
     *
     * @param urlNameString
     * @return
     */
    public static String httpGet(String urlNameString) {
        String result = "";
        BufferedReader in = null;

        try {
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
               /* Map<String, List<String>> map = connection.getHeaderFields();
                // 遍历所有的响应头字段
                for (String key : map.keySet()) {
                    System.out.println(key + "--->" + map.get(key));
                }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param map 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, Object> map) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.addRequestProperty("Content-Length", "256");
            conn.addRequestProperty("Authorization", Base64.encodeBytes((Constant.RYAPPID + ":" + map.get("dateStr")).getBytes()));
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //2.中文有乱码的需要将PrintWriter改为如下
            //out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
            // 发送请求参数
            JSONObject json = new JSONObject();
            json.put("chatRoomName", map.get("name"));
            json.put("creator", map.get("creator"));
            out.print(json);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("post推送结果：" + result);
        return result;
    }

    /**
     * md5加密
     *
     * @param strOriginal
     * @return
     */
    public static String md5Str;

    public static String toMD5(String strOriginal) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(strOriginal.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
            }
            md5Str = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Str;
    }

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else if (fileS < 1099511627776L){
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        } else {
            fileSizeString = df.format((double) fileS / 1099511627776L) + "TB";
        }
        return fileSizeString;
    }

    /**
     * 字符串转文件大小
     *
     * @param fileSize
     * @return
     */
    public static long formatFileSize(String fileSize) {
        long result = 0L;
        if (fileSize.contains("G")){
            fileSize = fileSize.replaceAll("G","");
            result = Long.valueOf(fileSize) * 1073741824L;
        }else if (fileSize.contains("T")){
            fileSize = fileSize.replaceAll("T","");
            result = Long.valueOf(fileSize) * 1073741824 * 1024L;
        }
        return result;
    }

}
