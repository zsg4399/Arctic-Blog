package com.hnust.zsg.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hnust.zsg.entity.vo.MyUserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class JwtUtil {
    //设置默认过期时间为一小时
    public static final long TOKEN_EXPIRETIME =  60 * 60 * 1000L;

    //设置秘钥明文
    public static final String JWT_KEY = "43&8r^%1(1rb7v@1+78-2*";


    /**
     * 获取系统当前UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成token，默认过期时间一小时
     *
     * @param object
     * @return
     */
    public static String createJWT(Object object) {
        String uuid = getUUID();
        return createJWTBuilder(object, uuid, TOKEN_EXPIRETIME,TimeUnit.SECONDS).compact();     //生产最终的jws;
    }

    /**
     * 生成token，过期时间根据传入的参数自定义
     *
     * @param object
     * @param expiretime 过期时间
     * @poram timeunit 时间单位
     * @return
     */
    public static String createJWT(Object object, Long expiretime, TimeUnit timeUnit) {
        String uuid = getUUID();
        return createJWTBuilder(object, uuid, expiretime,timeUnit).compact();     //生产最终的jws;
    }

    /**
     * JWT构建器
     *
     * @param object
     * @param uuid
     * @param expiretime 过期时间
     * @return
     */
    public static JwtBuilder createJWTBuilder(Object object, String uuid, Long expiretime,TimeUnit timeUnit)  {
        long currentTime = System.currentTimeMillis();        //获取系统当前时间
        SecretKey secretKey = generalKey();    //生成随机盐值
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;   //签名的加密算法
        expiretime=timeUnit.toMillis(expiretime);
        Date expireDate = new Date(currentTime + expiretime);   //过期具体时刻
        return Jwts.builder()
                .signWith(signatureAlgorithm, secretKey)   //指定签名算法及加盐
                .setId(uuid)                           //将uuid作为token的id
                .setIssuer("北极风")                    //设置签发人
                .setIssuedAt(new Date(currentTime))    //设置签发时间
                .setExpiration(expireDate)     //设置过期时间
                .setSubject(JacksonUtil.toJsonString(object));

    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return
     */
    public static SecretKey generalKey() {
        //byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        byte[] encodedKey = Base64.getMimeDecoder().decode(JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


    /**
     * 解析JWT
     *
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }


}

