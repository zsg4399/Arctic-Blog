package com.hnust.zsg.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnust.zsg.annotation.RateLimit;
import com.hnust.zsg.context.user.UserContext;
import com.hnust.zsg.context.user.UserContextHolder;
import com.hnust.zsg.convert.CommentConvert;
import com.hnust.zsg.entity.dto.CommentDTO;
import com.hnust.zsg.entity.po.CommentPO;
import com.hnust.zsg.entity.vo.CommentVO;
import com.hnust.zsg.enumeration.LimitType;
import com.hnust.zsg.service.CommentService;
import com.hnust.zsg.utils.JacksonUtil;
import com.hnust.zsg.utils.Result;
import com.hnust.zsg.utils.ValidataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/comments")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 用户给评论点赞功能
     * 同时使用自定义限流注解，限制每个用户每天只能对评论点赞二十次，防止恶意刷赞行为
     *
     * @return
     */
    @RateLimit(time = 1, unit = TimeUnit.DAYS, count = 20, type = LimitType.ID)
    @PostMapping("/like")
    public Result LikeComment(@RequestParam("userId") Long userId, @RequestParam("commentId") Long commentId) {

        return null;
    }

    @GetMapping("/article")
    public void getAllCommentsByArticleId(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("articleId") Long articleId, HttpServletResponse response) {
        if (page < 1 || pageSize < 1) {
            throw new IllegalArgumentException("参数非法，禁止查询");
        }
        IPage page1 = new Page(page, pageSize);
        IPage<CommentVO> page2 = commentService.getArticleComment(page1, articleId);

        Map map = new HashMap<>();
        map.put("hasmore", page2.getTotal() < pageSize ? false : true);
        map.put("page", page2);
        try {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JacksonUtil.toJsonString(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/talk")
    public Result getAllCommentsByTalkId(@RequestParam("talkId") Long talkId) {
        return null;
    }

    @GetMapping("/about")
    public void getAboutComments(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, HttpServletResponse response) {
        if (page < 1 || pageSize < 1) {
            throw new IllegalArgumentException("参数非法，禁止查询");
        }
        IPage<CommentPO> page1 = new Page<>(page, pageSize);
        IPage<CommentVO> page2 = commentService.getAboutComment(page1);
        Map map = new HashMap<>();
        map.put("hasmore", page2.getTotal() < pageSize ? false : true);
        map.put("page", page2);

        try {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JacksonUtil.toJsonString(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对于关于页进行评论，每小时最多发表五条评论
     *
     * @param commentDTO
     * @return
     */
    @RateLimit(type = LimitType.ID, count = 5, time = 1, unit = TimeUnit.HOURS)
    @PostMapping("/about")
    public Result addAboutComment(@RequestBody CommentDTO commentDTO) {
        UserContext userContext = UserContextHolder.getContext();
        Long userId = userContext.getMyUserVO().getId();
        CommentPO commentPO = CommentConvert.INSTANCE.CommentDTO_TO_CommentPO(commentDTO);
        commentPO.setUserId(userId);
        commentPO = ValidataUtil.validComment(commentPO);
        if (commentService.addAboutComment(commentPO) > 0) {
            return Result.ok("新增评论成功");
        }
        return Result.fail("新增评论失败，请稍后重试");

    }

    /**
     * 对于文章进行评论，每小时最多发表五条评论
     *
     * @param commentDTO
     * @param articleId
     * @return
     */
    @RateLimit(type = LimitType.ID, count = 5, time = 1, unit = TimeUnit.HOURS)
    @PostMapping("/article")
    public Result addArticleComment(@RequestBody CommentDTO commentDTO, @RequestParam("articleId") Long articleId) {
        UserContext userContext = UserContextHolder.getContext();
        Long userId = userContext.getMyUserVO().getId();
        CommentPO commentPO = CommentConvert.INSTANCE.CommentDTO_TO_CommentPO(commentDTO);
        commentPO.setUserId(userId);
        commentPO = ValidataUtil.validComment(commentPO);
        if (commentService.addArticleComment(commentPO, articleId) > 0) {
            return Result.ok("添加文章评论成功");
        }
        return Result.fail("新增文章评论失败");
    }

    @RateLimit(type = LimitType.ID, count = 5, time = 1, unit = TimeUnit.HOURS)
    @PostMapping("/talk")
    public Result addTalkComment(@RequestBody CommentPO commentPO, @RequestParam("talkId") Long talkId) {
        commentService.addTalkComment(commentPO, talkId);
        return null;
    }

    @RateLimit(type = LimitType.ID, count = 5, time = 1, unit = TimeUnit.HOURS)
    @PostMapping("/reply")
    public Result addReply(@RequestBody CommentDTO commentDTO) {
        if (commentDTO.getPid() == null) {
            return Result.fail("Bad Arguments");
        }
        UserContext userContext = UserContextHolder.getContext();
        Long userId = userContext.getMyUserVO().getId();
        CommentPO commentPO = CommentConvert.INSTANCE.CommentDTO_TO_CommentPO(commentDTO);
        commentPO.setUserId(userId);
        int result = commentService.addCommentReply(commentPO);
        if (result > 0) {
            return Result.ok("评论回复成功");
        }
        return Result.fail("评论回复失败");
    }
}
