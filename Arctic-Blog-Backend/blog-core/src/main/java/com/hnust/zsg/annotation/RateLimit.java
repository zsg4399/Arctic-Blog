package com.hnust.zsg.annotation;

import com.hnust.zsg.enumeration.LimitType;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 86187
 * 作用于对外提供的api方法上，用于接口限流
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    /**
     * 限流的key
     * @return
     */
    String key() default "rate_limit:";
    /**
     * 接口限流的单位时间
     *
     * @return
     */
    int time() default 60;

    /**
     * 时间单位，默认为秒级
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 单位时间内最大访问次数
     *
     * @return
     */
    int count() default 100;

    /**
     * 接口限制策略
     *
     * @return
     */
    LimitType type() default LimitType.DEFAULT;
}
