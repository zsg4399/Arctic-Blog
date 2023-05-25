package com.hnust.zsg.enumeration;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 */
@Getter
public enum ResultCodeType {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    PARAM_ERROR( 202, "参数不正确"),
    SERVICE_ERROR(203, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    DATA_UPDATE_ERROR(205, "数据版本异常"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),

    CODE_ERROR(210, "验证码错误"),
    LOGIN_USERNAME_ERROR(211, "用户名已被占用"),
    LOGIN_DISABLED_ERROR(212, "改用户已被禁用"),
    REGISTER_EMAIL_ERROR(213, "该邮箱已被绑定"),
    LOGIN_ACL(403, "无权访问"),

    URL_ENCODE_ERROR( 216, "URL编码失败"),
    ILLEGAL_CALLBACK_REQUEST_ERROR( 217, "非法回调请求"),
    FETCH_ACCESSTOKEN_FAILD( 218, "获取accessToken失败"),
    FETCH_USERINFO_ERROR( 219, "获取用户信息失败"),
    //LOGIN_ERROR( 23005, "登录失败"),
    INSERT_ARTICLE_ERROR(305,"文章发布失败"),


    SIGN_ERROR(300, "签名错误"),
    USERNAME_NOT_FOUND(423,"用户名不存在"),
    AUTHENTICATION_EXPIRE(424,"认证已经过期，请重新登录"),

    PASSWORD_ERROR(425,"密码错误"),
    JWT_ERROR(426,"token被篡改"),
    USER_HAS_LOGIN(427,"用户已登录，请注销后再进行登录"),
    ILLEAGL_MODIFY_USER(428,"非法修改他人账号信息"),
    ACCOUNT_DISABLED(429,"账号已被禁用")
    ;

    private Integer code;
    private String message;

    private ResultCodeType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
