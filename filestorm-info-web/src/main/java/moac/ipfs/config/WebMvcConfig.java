package moac.ipfs.config;


import moac.ipfs.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * MVC配置
 *
 * @author GZC
 * @email 57855143@qq.com
 * @date 2017-04-20 22:30
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/app/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/web/**");
    }
}