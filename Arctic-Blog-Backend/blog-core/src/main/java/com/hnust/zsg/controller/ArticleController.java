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
import com.hnust.zsg.entity.vo.ArticleSearchVO;
import com.hnust.zsg.enumeration.LimitType;
import com.hnust.zsg.enumeration.ResultCodeType;
import com.hnust.zsg.service.ArticleService;
import com.hnust.zsg.utils.JacksonUtil;
import com.hnust.zsg.utils.RedisUtil;
import com.hnust.zsg.utils.Result;
import com.hnust.zsg.utils.ValidataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

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
    public Result<IPage> getAllArticlesHot(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam("order") String order, @RequestParam(value = "userId", required = false) Long userId) {
        if (page < 1 || pageSize < 1 || userId.intValue() < 1 || (!order.equals("createTime") && !order.equals("articleLikes"))) {
            throw new IllegalArgumentException("非法参数查询");
        }
        Page<ArticlePO> page1 = new Page(page, pageSize);
        Page<ArticleListVO> page2 = articleService.getAllArticle(page1, order, userId);
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
     * 每个用户每天只能发布一篇文章
     *
     * @param
     * @return
     */
    @RateLimit(type = LimitType.ID, time = 1, count = 1, unit = TimeUnit.DAYS)
    @PostMapping()
    public Result addArticle(StandardMultipartHttpServletRequest request) {
        //获取图片文件流
        MultipartFile imageFile = request.getFile("image");
        //提取文章json字符串并将其反序列化成ArticleContentVO对象
        ArticleContentVO articleContentVO = JacksonUtil.parseObject(request.getParameter("article"), ArticleContentVO.class);
        articleContentVO.setAuthorId(UserContextHolder.getContext().getMyUserVO().getId());
        articleContentVO = ValidataUtil.ValidArticleContentVO(articleContentVO);
        try {
            articleService.addArticle(imageFile, articleContentVO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Result.set(ResultCodeType.INSERT_ARTICLE_ERROR);
        }
        return Result.set(ResultCodeType.SUCCESS);

    }

    @RateLimit(type = LimitType.ID, count = 20, time = 1, unit = TimeUnit.DAYS)
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
    @RateLimit(type = LimitType.ID, count = 5, time = 1, unit = TimeUnit.DAYS)
    @DeleteMapping()
    public Result<String> deleteArticleById(@RequestParam("id") Long id) {
        if (id == null || id.intValue() <= 0) {
            return Result.fail("请传递正确的文章id值");
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
     * 每日限流每个用户只能点赞20次文章
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
     * 每日限流每个用户只能收藏20次文章
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

    /**
     * 获取热度最高的四张图片作为首页轮播图
     *
     * @return
     */
    @GetMapping("/getSwiper")
    public Result getSwipper() {
        Map map = RedisUtil.hget(RedisUtil.HOME_SWIPPER_4);
        if (map.isEmpty()) {
            return Result.fail();
        }
        SwiperVO[] result = new SwiperVO[map.size()];
        for (int i = 0; i < map.size(); i++) {
            result[i] = JacksonUtil.parseObject((String) map.get(String.valueOf(i + 1)), SwiperVO.class);
        }
        return Result.ok(result);
    }

    @GetMapping("/search")
    public Result searchArticle(@RequestParam("message") String message) {
        if (message == null || (message = message.trim()).length() == 0 || message.length() > 25) {
            return Result.fail("请输入需要搜索的关键信息，并控制长度25个字以内");
        }
        ArticleSearchVO articleSearchVO = articleService.searchArticle(message);
        return Result.ok(articleSearchVO);
    }
}


