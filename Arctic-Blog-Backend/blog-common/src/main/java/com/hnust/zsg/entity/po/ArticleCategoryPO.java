package com.hnust.zsg.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@TableName("article_category")
@Data
@AllArgsConstructor
public class ArticleCategoryPO {
    @TableField("id")
    private Long id;

    @TableField("articleId")
    private Long articleId;

    @TableField("categoryId")
    private Long categoryId;
}
