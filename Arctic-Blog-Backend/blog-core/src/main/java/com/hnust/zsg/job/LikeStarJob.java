package com.hnust.zsg.job;

import com.hnust.zsg.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author 86187
 */
@Slf4j
@Component
public class LikeStarJob extends QuartzJobBean {
    @Autowired
    @Lazy
    private ArticleService articleService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
         Long startMills=System.currentTimeMillis();
         articleService.insertLikeAndStar();
         Long finishMills=System.currentTimeMillis();
         log.info("执行批量写入更新操作,耗时:{}ms",finishMills-startMills);
    }
}
