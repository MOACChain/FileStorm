package moac.ipfs.common.utils;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @program: moacipfs
 * @author: GZC
 * @create: 2018-09-11 10:10
 * @description: bean工具类
 **/
@Component
public class BeanUtils {

    /**
     * 序列化完成对象的深克隆
     * @return
     * @throws Exception
     */
    public static  <T> T deepClone(Object obj) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            // 将流序列化成对象
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            return (T)ois.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bos.close();
            if (oos != null){
                oos.close();
            }
            if (bis != null){
                bis.close();
            }
            if (ois != null){
                ois.close();
            }
        }
        return null;
    }
}
