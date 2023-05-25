package com.hnust.zsg.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnust.zsg.annotation.RateLimit;
import com.hnust.zsg.context.user.UserContext;
import com.hnust.zsg.context.user.UserContextHolder;
import com.hnust.zsg.entity.dto.SwiperVO;
import com.hnust.zsg.entity.po.ArticlePO;
import com.hnust.zsg.entity.vo.ArticleContentVO;
import com.hnust.zsg.entity.vo.ArticleListVO;
import com.hnust.zsg.enumeration.LimitType;
import com.hnust.zsg.enumeration.ResultCodeType;
import com.hnust.zsg.service.ArticleService;
import com.hnust.zsg.utils.JacksonUtil;
import com.hnust.zsg.utils.RedisUtil;
import com.hnust.zsg.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 分页查询文章列表，并且将已查询的文章信息放入Redis缓存中减轻数据库读写压力
     *
     * @param page
     * @param pageSize
     * @param order
     * @return
     */
    @GetMapping()
    public Result<IPage> getAllArticlesHot(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam("order") String order) {
        Page<ArticlePO> page1 = new Page(page, pageSize);
        Page<ArticleListVO> page2 = articleService.getAllArticle(page1, order);
        return Result.ok(page2);
    }


    /**
     * 根据文章id查找对应文章的详细内容
     *
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    public Result getArticleContentById(@PathVariable("id") Long id) {
        if (id == null) {
            return Result.fail("未查询到id为" + id + "的文章");
        }
        return Result.ok(articleService.findArticleById(id));
    }


    /**
     * 发布文章并启用事务处理
     *
     * @param articleContentVO
     * @return
     */
    @PostMapping()
    public Result addArticle(@RequestBody ArticleContentVO articleContentVO) {

        try {
            articleService.addArticle(articleContentVO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Result.set(ResultCodeType.INSERT_ARTICLE_ERROR);
        }
        return Result.set(ResultCodeType.SUCCESS);
    }


    @PutMapping()
    public Result modifyArticle(@RequestBody ArticleContentVO articleContentVO) {
        return null;
    }


    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @DeleteMapping()
    public Result<String> deleteArticleById(@RequestParam("id") Long id) {
        if (id == null) {
            return Result.fail("请传递正确的id值");
        }
        String deleteUrl = null;
        try {
            deleteUrl = articleService.deleteArticleById(id);
            if (deleteUrl == null) {
                return Result.fail("没有id为:" + id + "的文章存在");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Result.fail("文章删除失败");
        }
        return Result.ok(deleteUrl);
    }

    /**
     * 用户给文章点赞功能
     *
     * @return
     */
    @RateLimit(type = LimitType.ID, count = 20, time = 1, unit = TimeUnit.DAYS)
    @PostMapping("/like")
    public Result LikeArticle(@RequestParam("articleId") Long articleId, @RequestParam("islike") Boolean islike) {
        UserContext userContext = UserContextHolder.getContext();
        Long userId = userContext.getMyUserVO().getId();
        articleService.likeArticle(userId, articleId, islike);
        return Result.ok(!islike);
    }

    /**
     * 用户给文章收藏功能
     *
     * @return
     */
    @RateLimit(type = LimitType.ID, count = 20, time = 1, unit = TimeUnit.DAYS)
    @PostMapping("/star")
    public Result StarArticle(@RequestParam("articleId") Long articleId, @RequestParam("isStar") Boolean isStar) {
        UserContext userContext = UserContextHolder.getContext();
        Long userId = userContext.getMyUserVO().getId();
        articleService.starArticle(userId, articleId, isStar);
        return Result.ok(!isStar);
    }

    @GetMapping("getSwiper")
    public Result getSwipper() {
        Map map= RedisUtil.hget(RedisUtil.HOME_SWIPPER_4);
        if(map.isEmpty()){
            return Result.fail();
        }
        SwiperVO[] result=new SwiperVO[map.size()];
        for(int i=0;i< map.size();i++){
            result[i]=JacksonUtil.parseObject((String)map.get(String.valueOf(i+1)),SwiperVO.class);
        }
        return Result.ok(result);
    }
}


