package com.hnust.zsg.config;

import com.hnust.zsg.utils.JacksonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class RedisConfig {
    /**
     * 自定义Redistemplate
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object>  redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);


        //创建序列器对象，传入的objectmapper采用jacksonUtils中配置好的
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer=new GenericJackson2JsonRedisSerializer(JacksonUtil.getObjectMapper());
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();

        //设置string和hash类型key的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //设置string和hash类型value序列化的方式
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    /**
     * 接口限流专用的lua脚本
     * @return
     */
    @Bean
    public DefaultRedisScript<Long> LimitScript(){
        DefaultRedisScript<Long> script=new DefaultRedisScript<>();
        //读取lua脚本文件
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/limit.lua")));
        script.setResultType(Long.class);
        return script;
    }
}
