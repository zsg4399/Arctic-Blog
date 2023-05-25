package com.hnust.zsg;



import com.hnust.zsg.entity.po.CommentPO;
import com.hnust.zsg.entity.vo.MyUserVO;
import com.hnust.zsg.service.CommentService;
import com.hnust.zsg.service.impl.CommentServiceimpl;
import com.hnust.zsg.utils.EmailUtil;
import com.hnust.zsg.utils.ValidataUtil;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@SpringBootTest
class MyBlogApplicationTests {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private CommentService commentService;

    @Test
    void testSQL() throws Exception {
        CommentPO commentPO=new CommentPO();
        commentPO.setContent("11");
        commentPO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        commentPO.setUserId(1L);
        commentService.addAboutComment(commentPO);
    }
}
