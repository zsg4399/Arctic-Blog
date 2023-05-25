package com.hnust.zsg.entity.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@TableName("comment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentPO {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("userId")
    private Long userId;

    @TableField("createTime")
    private Timestamp createTime;

    @TableField("commentLikes")
    private Long commentLikes;

    @TableField("content")
    private String content;

    @TableField("replyUid")
    private Long replyUid;

    @TableField("pid")
    private Long pid;

}
