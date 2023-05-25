package com.hnust.zsg.job;

import com.hnust.zsg.service.ArticleService;
import com.hnust.zsg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DailyJob extends QuartzJobBean {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;


    /**
     * 这里是每日执行一次的定时任务
     * 1. 每日将最热门的文章的图片从MySQL读取到Redis中，允许一天的数据不一致性
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        articleService.SwipperToRedis();
        userService.setAuthorInfoToRedis();
    }
}
