package moac.ipfs.interceptor;


import io.jsonwebtoken.Claims;
import moac.ipfs.common.annotation.Login;
import moac.ipfs.common.utils.CfgUtils;
import moac.ipfs.common.exception.RRException;
import moac.ipfs.common.utils.JwtUtils;
import moac.ipfs.common.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 * @author GZC
 * @email 57855143@qq.com
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private CfgUtils cfgUtils;

    public static final String USER_KEY = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (cfgUtils.isIgnoreLogin()){
            return true;
        }
        Login annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }

        if(annotation == null){
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(jwtUtils.getHeader());
        if(StringUtils.isBlank(token)){
            token = request.getParameter(jwtUtils.getHeader());
        }

        //凭证为空
        if(StringUtils.isBlank(token)){
            throw new RRException("未登录！", HttpStatus.UNAUTHORIZED.value());
        }

        Claims claims = jwtUtils.getClaimByToken(token);
        if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
            throw new RRException("登录失效，请重新登录！", HttpStatus.UNAUTHORIZED.value());
        }
        /**
         *凭证不为空，去redis里取，取不到或取到的token与此凭证不相同，则此凭证无效
         */
        Integer userId= Integer.parseInt(claims.getSubject());
        String redisToken=redisUtils.get("jwt"+userId);
        if(redisToken == null || redisToken.trim() == "" || token.equals(redisToken) == false){
            throw new RRException("登录失效，请重新登录！", HttpStatus.UNAUTHORIZED.value());
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, Long.parseLong(claims.getSubject()));

        return true;
    }
}
