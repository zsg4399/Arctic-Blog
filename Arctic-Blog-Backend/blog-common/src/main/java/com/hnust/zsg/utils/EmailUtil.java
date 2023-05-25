package com.hnust.zsg.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;

public class EmailUtil {

    /**
     * 邮件信息工厂
     * @param code 验证码
     * @param email  邮箱地址
     */
    public static SimpleMailMessage messageFactory(String code,String email){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject("北极风个人博客网站正在进行邮箱注册验证");
        message.setFrom("1064485698@qq.com");
        message.setTo(email);
        String text=new StringBuilder("你正在进行登录操作，验证码:").append(code).append("，切勿将验证码泄露给他人，本条验证码有效期10分钟。").toString();
        message.setText(text);
        return message;
    }
}
