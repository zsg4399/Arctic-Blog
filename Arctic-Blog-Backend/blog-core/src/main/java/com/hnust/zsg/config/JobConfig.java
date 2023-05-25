package com.hnust.zsg.config;

import com.hnust.zsg.job.DailyJob;
import com.hnust.zsg.job.LikeStarJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {
    @Bean
    public JobDetail job1() {
        return JobBuilder
                .newJob(LikeStarJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger job1Trigger() {
        //构造简单调度计划的构造器
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(12)  //设置定时任务的频率,每12h跑一次
                .repeatForever();
        return TriggerBuilder
                .newTrigger()
                .forJob(job1())     //配置触发器对应的任务
                .withIdentity("demo1Trigger")
                .withSchedule(simpleScheduleBuilder)   //配置调度器
                .build();
    }

    @Bean
    public JobDetail Job2() {
        return JobBuilder
                .newJob(DailyJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger Job2Trigger() {
        //构造简单调度计划的构造器
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(24)  //设置定时任务的频率,每24h跑一次
                .repeatForever();
        return TriggerBuilder
                .newTrigger()
                .forJob(Job2())     //配置触发器对应的任务
                .withIdentity("Job2Trigger")
                .withSchedule(simpleScheduleBuilder)   //配置调度器
                .build();
    }

}


