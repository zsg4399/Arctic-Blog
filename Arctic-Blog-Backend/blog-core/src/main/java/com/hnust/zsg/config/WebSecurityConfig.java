package com.hnust.zsg.config;

import com.hnust.zsg.filiter.TokenAuthenticationFiliter;
import com.hnust.zsg.filiter.TokenLoginFiliter;
import com.hnust.zsg.handler.MyAccessDeniedHandler;
import com.hnust.zsg.service.UserService;
import com.hnust.zsg.service.impl.UserServiceimpl;
import com.hnust.zsg.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userServiceimpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    @Autowired
    private UserService userServiceimpl;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/login","/users/register","/users/createcode","/articles/**","/articles/details/**").anonymous()
                .anyRequest().authenticated()  //除了注册和登录相关功能的请求，其他均需要认证
                .and()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler)      //权限校验失败处理器
                .and()
                .csrf()               //关闭CSRF过滤器,采用jwt进行权限认证
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        //将token解析认证过滤器添加到UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(new TokenAuthenticationFiliter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new TokenLoginFiliter(authenticationManager()),UsernamePasswordAuthenticationFilter.class);
        http.cors();    //解决跨域问题


    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

}
