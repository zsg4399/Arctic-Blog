package com.hnust.zsg.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "spring.elasticsearch")
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    /**
     * host 目标主机的ip或者域名
     */
    private String host = "127.0.0.1";
    /**
     * port elasticsearch运行的端口号
     */
    private int port = 9200;
    /**
     * 尝试获取连接池的最大超时时间，超时将会抛出异常,单位为s
     */
    private int tryConnTimeout = 1;
    /*
    同时间的最大连接数
     */
    private int maxConnectNum = 20;
    /*
    每个域名同时最多使用的连接数
     */
    private int maxPerRoute = 20;
    /**
     * 连接建立的超时时间，超时将会抛出异常
     */
    private int connectionTimeout = 1;
    /**
     * 从服务端读取数据的时间，超时将会抛出异常
     */
    private int socketTimeout = 30;

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTryConnTimeout() {
        return tryConnTimeout;
    }

    public void setTryConnTimeout(int tryConnTimeout) {
        this.tryConnTimeout = tryConnTimeout;
    }

    public int getMaxConnectNum() {
        return maxConnectNum;
    }

    public void setMaxConnectNum(int maxConnectNum) {
        this.maxConnectNum = maxConnectNum;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)).setRequestConfigCallback(requestbuilder ->
                requestbuilder
                        //指客户端和服务器建立连接的timeout.就是http请求的三个阶段，一：建立连接；二：数据传送；三，断开连接。
                        // 如果与服务器(这里指数据库)请求建立连接的时间超过ConnectionTimeOut，就会抛 ConnectionTimeOutException。
                        .setConnectTimeout((int) TimeUnit.SECONDS.toMillis(connectionTimeout))
                        //指客户端从服务器读取数据的timeout超出预期设定时间，超出后会抛出SocketTimeOutException.
                        .setSocketTimeout((int) TimeUnit.SECONDS.toMillis(socketTimeout))
                        //设置从连接池中取出连接的最大等待时间，如果连接池里连接都被用了，且超过设定时间,就会报错connectionrequesttimeout，会抛出超时异常
                        .setConnectionRequestTimeout((int) TimeUnit.SECONDS.toMillis(tryConnTimeout))
        ).setHttpClientConfigCallback(httpAsyncClientBuilder ->
                httpAsyncClientBuilder
                        //设置最多连接数
                        .setMaxConnTotal(maxConnectNum)
                        //设置请求同一路由同时占用的最大连接数，这里为elasticsearch服务器运行的机器的地址，由于单体部署所以只会产生一个路由
                        .setMaxConnPerRoute(maxPerRoute)
        ));
    }

    /**
     * ElasticsearchRestTemplate是对RestHighLevelClient的进一步封装，构造方法将RestHighLevelClient实例对象传入即可
     *
     * @return
     */
    @Bean
    public ElasticsearchRestTemplate getMyElasticSearchRestTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}
