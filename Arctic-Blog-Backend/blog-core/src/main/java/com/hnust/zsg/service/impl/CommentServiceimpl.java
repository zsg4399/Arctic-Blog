package com.hnust.zsg.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.zsg.convert.CommentConvert;
import com.hnust.zsg.entity.po.CommentPO;
import com.hnust.zsg.entity.vo.CommentVO;
import com.hnust.zsg.entity.vo.ReplyVO;
import com.hnust.zsg.entity.vo.UserVO;
import com.hnust.zsg.mapper.ArticleMapper;
import com.hnust.zsg.mapper.CommentMapper;
import com.hnust.zsg.mapper.TalkMapper;
import com.hnust.zsg.mapper.UserMapper;
import com.hnust.zsg.service.CommentService;
import com.hnust.zsg.utils.ValidataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceimpl extends ServiceImpl<CommentMapper, CommentPO> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TalkMapper talkMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.REPEATABLE_READ)
    public int addAboutComment(CommentPO commentPO) {
        commentMapper.addComment(commentPO);
        Long commentId = commentPO.getId();
        return commentMapper.addAboutComment(commentId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.REPEATABLE_READ)
    public int addArticleComment(CommentPO commentPO, Long articleId) {
        commentMapper.addComment(commentPO);
        Long commentId = commentPO.getId();
        return commentMapper.addArticleComment(articleId, commentId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.REPEATABLE_READ)
    public int addTalkComment(CommentPO commentPO, Long talkId) {
        commentMapper.addComment(commentPO);
        Long commentId = commentPO.getId();
        return commentMapper.addTalkComment(talkId, commentId);
    }

    @Transactional(rollbackFor = RuntimeException.class, timeout = 5, isolation = Isolation.REPEATABLE_READ)
    @Override
    public IPage<CommentVO> getAboutComment(IPage<CommentPO> page) {
        IPage<CommentPO> page1 = commentMapper.getAboutComment(page);
        return page1.convert(this::apply);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, timeout = 10, rollbackFor = RuntimeException.class)
    @Override
    public int addCommentReply(CommentPO commentPO) {
        commentPO = ValidataUtil.validComment(commentPO);
        int result = commentMapper.addCommentReply(commentPO);
        return result;
    }

    @Override
    public IPage<CommentVO> getArticleComment(IPage page1, Long articleId) {

        IPage<CommentPO> page2 = commentMapper.getArticleComment(page1, articleId);
        return page2.convert(this::apply);
    }

    /**
     * 将查询出来的评论进行一定的转换，获得最终展现给用户的效果
     * @param item
     * @return
     */
    public CommentVO apply(CommentPO item) {

        Long userId = item.getUserId();
        UserVO user = userMapper.getUserById(userId);
        CommentVO commentVO = CommentConvert.INSTANCE.POTOVO(item);
        commentVO.setAvatar(user.getAvatar());
        commentVO.setUsername(user.getUsername());
        List<CommentPO> commentList = commentMapper.getReplyByPid(item.getId());
        List<ReplyVO> replyVOList = commentList.stream().map(e -> {
            ReplyVO replyVO = CommentConvert.INSTANCE.CommentPO_TO_ReplyVO(e);
            Long uid = e.getUserId();
            UserVO userVO = userMapper.getUserById(uid);

            replyVO.setUsername(userVO.getUsername());
            replyVO.setAvatar(userVO.getAvatar());
            replyVO.setUid(uid);
            Long replyId = e.getReplyUid();
            if (!replyId.equals(0L)) {
                String reply_user = userMapper.getUsernameById(replyId);
                replyVO.setReply_user(reply_user);
            }
            return replyVO;
        }).collect(Collectors.toList());
        commentVO.setChildren(replyVOList);
        return commentVO;
    }


}
