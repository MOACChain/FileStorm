package moac.ipfs.common.annotation;

import java.lang.annotation.*;

/**
 * app登录效验
 * @author GZC
 * @email 57855143@qq.com
 * @date 2017/9/23 14:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
