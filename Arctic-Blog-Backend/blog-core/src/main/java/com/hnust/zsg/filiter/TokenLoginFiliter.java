package com.hnust.zsg.filiter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnust.zsg.Excption.validation.AccountDisabledException;
import com.hnust.zsg.Excption.validation.PasswordillegalException;
import com.hnust.zsg.Excption.validation.UsernameillegalException;
import com.hnust.zsg.entity.vo.LoginUserVO;
import com.hnust.zsg.entity.vo.MyUserVO;
import com.hnust.zsg.enumeration.ResultCodeType;
import com.hnust.zsg.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录请求过滤器
 */
@Slf4j
public class TokenLoginFiliter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/users/login", "POST");
    private String usernameParameter = "username";
    private String passwordParameter = "password";
    private boolean postOnly = true;

    public TokenLoginFiliter() {
        //修改默认匹配地址
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public TokenLoginFiliter(AuthenticationManager manager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, manager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            //springsecurity默认是不支持从request中读取json字符串的，所以采用fastjson的提供的方法来获取请求中的json字符串
            LoginUserVO userVO = objectMapper.readValue(request.getInputStream(), LoginUserVO.class);
            String username = userVO.getUsername();
            username = username != null ? username.trim() : "";
            username = username.trim();
            String password = userVO.getPassword();
            password = password != null ? password.trim() : "";
            //根据用户名和密码生成一个认证token
            try {
                ValidataUtil.Valid(username, password);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                //设置认证信息
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (UsernameillegalException e1) {
                log.info(e1.getMessage());
                throw new UsernameNotFoundException("用户名格式错误");
            } catch (PasswordillegalException e2) {
                log.info(e2.getMessage());
               throw new BadCredentialsException("密码格式错误");
            }

        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        MyUserVO user = (MyUserVO) authResult.getPrincipal();
        user.setEnabled(null);
        user.setPassword(null);
        user.setUsername(null);
        String login_user_key = new StringBuilder(RedisUtil.LOGIN_USER_TOKEN).append(user.getId()).toString();
        if (RedisUtil.hasKey(login_user_key)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JacksonUtil.toJsonString(Result.set(ResultCodeType.USER_HAS_LOGIN)));
            return;
        }
        user.setPassword(null);
        Map<String, Object> map = new HashMap<>();
        //设置access_token默认过期时间为一小时
        String access_token = JwtUtil.createJWT(user);
        //设置刷新令牌七天的过期时间
        String refresh_token = JwtUtil.createJWT(user, 7L, TimeUnit.DAYS);
        //在Redis中缓存两小时
        RedisUtil.set(access_token, refresh_token, 7L, TimeUnit.DAYS);
        String key = new StringBuilder(RedisUtil.LOGIN_USER_TOKEN).append(user.getId()).toString();
        RedisUtil.set(key, 1, 7L, TimeUnit.DAYS);
        map.put("access_token", access_token);
        map.put("userinfo", user);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JacksonUtil.toJsonString(Result.ok(map)));

    }

    /**
     * 相当于自定义了一个AuthenticationEntryPoint处理验证失败
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed.getClass() == UsernameNotFoundException.class) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JacksonUtil.toJsonString(Result.set(ResultCodeType.USERNAME_NOT_FOUND)));
        } else if (failed.getClass() == BadCredentialsException.class) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JacksonUtil.toJsonString(Result.set(ResultCodeType.PASSWORD_ERROR)));
        }
        if(failed.getClass()== AccountDisabledException.class){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JacksonUtil.toJsonString(Result.set(ResultCodeType.ACCOUNT_DISABLED)));
        }

    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return this.usernameParameter;
    }

    public final String getPasswordParameter() {
        return this.passwordParameter;
    }
}
