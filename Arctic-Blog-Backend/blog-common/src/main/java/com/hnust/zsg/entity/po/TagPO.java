package com.hnust.zsg.entity.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("tag")
public class TagPO {
    @TableId("id")
    private Long id;

    @TableField("articleId")
    private Long articleId;

    @TableField("tagName")
    private String tagName;

    @TableField("authorId")
    private Long authorId;

    private TagPO(){}
}
