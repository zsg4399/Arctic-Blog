package com.hnust.zsg.config;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    /**
     * localdatetime类用来获取当前系统的时间，且线程安全，为jdk1.8之后推荐使用的日期类
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insertFill start ...");


        this.setFieldValByName("createTime",LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("updateFill start ...");

        this.setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
    }
}
