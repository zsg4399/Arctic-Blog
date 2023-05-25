package com.hnust.zsg.enumeration;

/**
 * 限制策略类型，有以下几种策略
 * id
 * default
 * ip
 */
public enum LimitType {
    /**
     * 默认值，接口在限定时间内只能被访问有限次
     */
    DEFAULT,
    /**
     * 根据IP地址进行限流，一个IP对一个接口在限定时间内只能访问有限次
     */
    IP,
    /**
     * 根据用户ID进行限流，一个用户对一个接口在限定时间内只能访问有限次数
     */
    ID,


}
