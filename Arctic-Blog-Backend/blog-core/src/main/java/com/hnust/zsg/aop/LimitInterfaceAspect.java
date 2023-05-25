package com.hnust.zsg.aop;

import com.hnust.zsg.Exception.ServiceLimitException;
import com.hnust.zsg.annotation.RateLimit;
import com.hnust.zsg.context.user.UserContext;
import com.hnust.zsg.context.user.UserContextHolder;
import com.hnust.zsg.entity.vo.MyUserVO;
import com.hnust.zsg.enumeration.LimitType;
import com.hnust.zsg.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 86187
 */
@Aspect
@Component
@Slf4j
public class LimitInterfaceAspect {
    @Autowired
    private RedisScript redisScript;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 对接口进行限流
     */
    @Before(value = "limitBefore(rateLimit)", argNames = "joinPoint,rateLimit")
    public void LimitBefore(JoinPoint joinPoint, RateLimit rateLimit) {

        //获取注解中的属性配置
        String key = rateLimit.key();
        int time = rateLimit.time();
        TimeUnit unit = rateLimit.unit();
        int count = rateLimit.count();
        LimitType type = rateLimit.type();

        List<String> keys = Collections.singletonList(getCombineKey(rateLimit, joinPoint));
        Long finalTime = getFinalTime(time, unit);

        Long nunber = (Long) redisTemplate.execute(redisScript, keys, count, finalTime);
        log.info("请求key：{},请求次数:{}", keys, nunber);
        if (nunber == null || nunber.intValue() > count) {
            throw new ServiceLimitException("接口访问过于频繁，请稍后重试");
        }


    }

    /**
     * 手动声明注解的切入点
     *
     * @param rateLimit
     */
    @Pointcut(value = "@annotation(rateLimit)")
    public void limitBefore(RateLimit rateLimit) {

    }

    /**
     * 根据注解中的type生成最终key
     *
     * @param rateLimiter
     * @param point
     * @return
     */
    public String getCombineKey(RateLimit rateLimiter, JoinPoint point) {
        StringBuffer stringBuffer = new StringBuffer(rateLimiter.key());
        if (rateLimiter.type() == LimitType.IP) {
            stringBuffer.append(IPUtil.getIpAddr(((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.currentRequestAttributes())).getRequest())).append("-");
        } else if (rateLimiter.type() == LimitType.ID) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            UserContext userContext= UserContextHolder.getContext();
            MyUserVO myUserVO=userContext.getMyUserVO();
            Long userId=myUserVO.getId();
            stringBuffer.append("userId:" + userId + "-");
        }
        //获取方法及其对应的类名，并添加到key中
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuffer.toString();
    }

    public Long getFinalTime(int time, TimeUnit timeUnit) {
        Long finalTime = Long.valueOf(time);
        finalTime = timeUnit.toSeconds(finalTime);
        return finalTime;
    }
}








