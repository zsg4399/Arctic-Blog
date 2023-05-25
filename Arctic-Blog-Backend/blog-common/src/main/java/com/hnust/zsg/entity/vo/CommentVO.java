package com.hnust.zsg.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
    private Long id;
    private String content;
    private String avatar;
    private String username;
    private String createTime;
    private Long commentLikes;
    private List<ReplyVO> children;
}
