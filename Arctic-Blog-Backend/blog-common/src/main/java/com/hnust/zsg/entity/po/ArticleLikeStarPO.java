package com.hnust.zsg.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@TableName("article_like_star")
public class ArticleLikeStarPO implements Serializable {
    public ArticleLikeStarPO(){}
    private static final long serialVersionUID=9898131381L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("articleId")
    private Long articleId;

    @TableField("userId")
    private Long userId;

    @TableField("isLike")
    private Boolean isLike;

    @TableField("isStar")
    private Boolean isStar;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;



}
