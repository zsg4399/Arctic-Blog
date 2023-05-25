package com.hnust.zsg.entity.vo;


import com.hnust.zsg.entity.po.CategoryPO;
import com.hnust.zsg.entity.po.CommentPO;
import com.hnust.zsg.entity.po.TagPO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class ArticleContentVO {
    private String title;
    private String imageUrl;
    private String summary;
    private String articleRaw;
    private String articleHtml;
    private Long authorId;
    private String deleteUrl;

    private List<CategoryPO> categorys;
    private List<TagPO> tags;

    public ArticleContentVO(){}
}
