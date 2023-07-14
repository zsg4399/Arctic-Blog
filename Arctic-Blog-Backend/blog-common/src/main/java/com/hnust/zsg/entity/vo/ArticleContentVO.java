package com.hnust.zsg.entity.vo;


import com.hnust.zsg.entity.po.CategoryPO;
import com.hnust.zsg.entity.po.CommentPO;
import com.hnust.zsg.entity.po.TagPO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class ArticleContentVO {
    private String title;
    private String summary;
    private String articleRaw;
    private Long authorId;

    private List<CategoryPO> categorys;
    private List<TagPO> tags;

    public ArticleContentVO(){}
}
