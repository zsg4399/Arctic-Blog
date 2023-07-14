package com.hnust.zsg.filiter;


import com.hnust.zsg.entity.vo.MyUserVO;
import com.hnust.zsg.enumeration.ResultCodeType;
import com.hnust.zsg.context.user.UserContext;
import com.hnust.zsg.context.user.UserContextHolder;
import com.hnust.zsg.utils.JacksonUtil;
import com.hnust.zsg.utils.JwtUtil;
import com.hnust.zsg.utils.RedisUtil;
import com.hnust.zsg.utils.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * JWT认证解析过滤器
 */
@Slf4j
public class TokenAuthenticationFiliter extends OncePerRequestFilter {
    public TokenAuthenticationFiliter() {
    }


    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static final String[] accessable = {  "/users/AuthorInfo", "/articles","/articles/*", "/articles/details/*", "/comments/*"};

    /**
     * 根据请求路径和请求方法判断是否需要保存登录状态的token信息
     *
     * @param url
     * @return
     */
    private boolean urlMatch(String url) {
        for (int i=0;i<accessable.length;i++) {
            if (antPathMatcher.match(accessable[i], url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断请求是否需要携带Authication请求头，如果不需要则放行，如果需要则进行token的解析并将其解析出来的用户信息存入上下文，避免同一个请求路径需要多次解析token
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        log.info("uri:{},method:{}", uri, method);
        //如果请求的接口是get请求，则进行urlMatch
        if ((uri.equals("/users/login"))||uri.equals("/users/register") ||uri.equals("/users/createcode")|| method.equals("GET") && urlMatch(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        String access_token = request.getHeader("Authorization");
        response.setContentType("application/json;charset=utf-8");
        if (StringUtils.hasText(access_token)) {
            //解析token
            Claims claims = null;
            try {
                claims = JwtUtil.parseJWT(access_token);
                //获取jwt中的用户信息
                MyUserVO user = JacksonUtil.parseObject(claims.getSubject(), MyUserVO.class);
                UserContext userContext = UserContextHolder.getContext();
                //将用户信息存储到上下文中
                userContext.setCurrentUserVO(user);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);      //继续执行过滤器链
            } catch (ExpiredJwtException e) {         //令牌过期
                String refresh_token = (String) RedisUtil.get(access_token);
                if (!StringUtils.hasText(refresh_token)) {
                    response.getWriter().write(JacksonUtil.toJsonString(Result.set(ResultCodeType.AUTHENTICATION_EXPIRE)));
                    return;
                }
                try {

                    claims = JwtUtil.parseJWT(refresh_token);
                    String new_access_token = JwtUtil.createJWT(claims);
                    RedisUtil.set(new_access_token, refresh_token, 2L, TimeUnit.HOURS);
                    Map<String, String> map = new HashMap<>();
                    map.put("access_token", new_access_token);
                    response.getWriter().write(JacksonUtil.toJsonString(map));
                    MyUserVO user = JacksonUtil.parseObject(claims.getSubject(), MyUserVO.class);
                    UserContext userContext = UserContextHolder.getContext();
                    //将用户信息存储到上下文中
                    userContext.setCurrentUserVO(user);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (ExpiredJwtException ex) {
                    ex.printStackTrace();
                    response.getWriter().write(JacksonUtil.toJsonString(Result.set(ResultCodeType.AUTHENTICATION_EXPIRE)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    response.getWriter().write(JacksonUtil.toJsonString(Result.set(ResultCodeType.JWT_ERROR)));
                } finally {
                    RedisUtil.expire(access_token, -1L);
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().write(JacksonUtil.toJsonString(Result.set(ResultCodeType.JWT_ERROR)));
            }
            return;

        }
        response.getWriter().write(JacksonUtil.toJsonString(Result.set(ResultCodeType.LOGIN_AUTH)));
        return;
    }
}
