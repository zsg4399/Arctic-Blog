package com.hnust.zsg.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpUtils {
    private static Long timeout = 2L;
    private static OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(timeout, TimeUnit.MINUTES)
            .readTimeout(timeout, TimeUnit.MINUTES)
            .writeTimeout(timeout, TimeUnit.MINUTES)
            .retryOnConnectionFailure(false)
            .build();

    /**
     * @param body        请求体
     * @param retry_count 请求超时重试次数
     * @return
     * @throws IllegalAccessException
     */
    public static Response uploadImage(RequestBody body, long retry_count) {
        if (retry_count < 0) {
            throw new IllegalArgumentException();
        }
        /**
         * 配置允许两分钟无连接
         * 设置默认的连接失败后不重试
         */
        Request request1 = new Request.Builder()
                .url("https://smms.app/api/v2/upload")
                .method("POST", body)
                .addHeader("Authorization", "your personal token")
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .addHeader("Content-Type", "multipart/form-data")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "smms.app")
                .addHeader("Connection", "keep-alive")
                .build();
        Response result=null;
        try {
           result= client.newCall(request1).execute();
        } catch (IOException e) {
            log.error("Connect Smms.app Server Timeout");
        }
        if (result == null && retry_count > 0) {
            result = uploadImage(body, retry_count - 1);
        }
        return result;
    }
}
