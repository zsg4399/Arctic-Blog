package com.hnust.zsg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hnust.zsg.entity.po.CommentPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<CommentPO>{
    Long getCommentAmountByArticleId(@Param("id") Long id);

    int addAboutComment(@Param("id")Long id);

    void addComment(CommentPO commentPO);

    int addArticleComment(@Param("articleId") Long articleId, @Param("commentId") Long commentId);

    int addTalkComment(@Param("talkId") Long talkId, @Param("commentId") Long commentId);

    IPage<CommentPO> getAboutComment(IPage<CommentPO> page);

    IPage<CommentPO> getTalkComment(IPage<CommentPO> page,Long talkId);

    IPage<CommentPO> getArticleComment(IPage<CommentPO> page,@Param("articleId") Long articleId);

    List<CommentPO> getReplyByPid(@Param("pid") Long pid);

    int addCommentReply(CommentPO commentPO);
}
