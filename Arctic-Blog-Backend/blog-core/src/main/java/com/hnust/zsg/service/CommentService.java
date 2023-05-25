package com.hnust.zsg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.zsg.entity.po.CommentPO;
import com.hnust.zsg.entity.vo.CommentVO;

public interface CommentService extends IService<CommentPO> {
    /**
     * 添加about页面的comment
     * @param commentPO
     * @return
     */
    int addAboutComment(CommentPO commentPO);

    /**
     * 根据文章id添加评论
     * @param commentPO
     * @param articleId
     * @return
     */
    int addArticleComment(CommentPO commentPO, Long articleId);

    /**
     * 根据说说的id添加评论
     * @param commentPO
     * @param talkId
     * @return
     */
    int addTalkComment(CommentPO commentPO, Long talkId);

    /**
     * 获取about页面的评论
     * @param page1
     * @return
     */
    IPage<CommentVO> getAboutComment(IPage<CommentPO> page1);

    /**
     * 对评论
     * @param commentPO
     * @return
     */
    int addCommentReply(CommentPO commentPO);

    /**
     *根据文章id获取指定文章的评论，同时根据page1进行分页加载
     * @param page1
     * @param articleId
     * @return
     */
    IPage<CommentVO> getArticleComment(IPage page1, Long articleId);
}
