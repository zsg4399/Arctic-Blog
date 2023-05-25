package com.hnust.zsg.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("article")
@Data
@AllArgsConstructor
public class ArticlePO implements Serializable {
    public ArticlePO(){};
    private static final long seriaVersionUID=31937139L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("title")
    private String title;

    @TableField("imageUrl")
    private String imageUrl;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("articleLikes")
    private Long articleLikes;

    @TableField("articleViews")
    private Long articleViews;

    @TableField("authorId")
    private Long authorId;

    @TableField("summary")
    private String summary;

    @TableField("articleStars")
    private Long articleStars;

    @TableField("deleteUrl")
    private String deleteUrl;
}
