package com.hnust.zsg.entity.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@TableName("article_tag")
@Data
@AllArgsConstructor
public class ArticleTagPO {
    @TableId("id")
    private Long id;

    @TableField("articleId")
    private Long articleId;

    @TableField("tagId")
    private Long tagId;
}
