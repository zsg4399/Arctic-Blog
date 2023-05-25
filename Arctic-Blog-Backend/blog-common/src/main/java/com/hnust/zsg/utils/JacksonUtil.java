package com.hnust.zsg.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hnust.zsg.deserializer.SimpleGrantedAuthorityDeserializer;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * fastjson存在众多安全漏洞，弃用，选择springboot内置的Jackson进行序列化操作
 */
public class JacksonUtil {
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * cofigure设置的是反序列化的一些配置
     * ACCEPT_SINGLE_VALUE_AS_ARRAY，接受能够将json中的简单类型的值反序列化成为Java中的数组/集合对象
     * FAIL_ON_UNKNOWN_PROPERTIES 设置为false，表示关闭反序列化时如果某一个属性没有setter方法，也会继续运行不抛异常，设置为true此时则会抛出JsonMappingException
     * 设置日期类型序列化时候的日期格式
     * 设置只有非空且不为“”的时候才会进行序列化
     * registerModule注册SimpleGrantedAuthority反序列化器,注册localdatetime序列化器和反序列化器
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .registerModule(new SimpleModule()
                    .addDeserializer(SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer())
                    .addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                    .addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
            );




    /**
     * 将obj对象解析成json字符串
     *
     * @param obj:
     * @return java.lang.String
     */
    public static String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串解析成Java中集合对象
     * 泛型T代表解析出来的对象类型
     *
     * @param json:
     * @param tTypeReference:里面的泛型是复杂对象的类型，list，map等必须通过这个对象传入
     * @return T
     */
    public static <T> T parseObject(String json, TypeReference<T> tTypeReference) {
        //对字符串进行专门判空
        if (!StringUtils.hasText(json)) {
            return null;
        }
        if (tTypeReference == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, tTypeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取ObjectMapper
     *
     * @return com.fasterxml.jackson.databind.ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * 构建ObjectNode
     *
     * @return com.fasterxml.jackson.databind.node.ObjectNode
     */
    public static ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }


    /*
    public static Map beanToMap(Object object)  {
        Map<String, Object> map = new HashMap<String, Object>();
        //利用反射获取对象中所有的属性名和对应值
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    */

    /**
     * bean转map,利用的是spring内置的beanMap将bean对象转成map
     *
     * @param object
     * @return
     */
    public static  Map<String, Object> beanToMap(Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (object != null) {
            BeanMap beanMap = BeanMap.create(object);
            Set<String> keys = beanMap.keySet();
            for (String key : keys) {
                map.put(key,  beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map集合转成Javabean对象
     *
     * @param map
     * @param clazz:Javabean的类对象
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        String mapString = toJsonString(map);
        return parseObject(mapString, clazz);
    }

    /**
     * 将json字符串解析成JavaBean类型的对象,需要传入对象的Class类型
     *
     * @param json
     * @param bean
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T parseObject(String json, Class<T> bean) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, bean);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
