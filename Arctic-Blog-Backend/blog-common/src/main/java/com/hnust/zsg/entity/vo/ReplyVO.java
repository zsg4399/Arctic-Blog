package com.hnust.zsg.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyVO {
    private Long id;
    private String content;
    private Long uid;
    private String avatar;
    private String username;
    private String createTime;
    private Long commentLikes;
    private String reply_user;
}
