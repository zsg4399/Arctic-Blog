package com.hnust.zsg.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("comment_like")
public class CommentLikePO {
    private static final long serialVersionUID=98981313881L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("commmentId")
    private Long articleId;

    @TableField("userId")
    private Long userId;

    @TableField("like")
    private Boolean like;
}
