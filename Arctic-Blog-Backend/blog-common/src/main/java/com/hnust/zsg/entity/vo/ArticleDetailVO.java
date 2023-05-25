package com.hnust.zsg.entity.vo;

import com.hnust.zsg.entity.po.CategoryPO;
import com.hnust.zsg.entity.po.TagPO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ArticleDetailVO {
    public ArticleDetailVO(){}
    private Long id;
    private String title;
    private String imageUrl;
    private String summary;
    private String articleRaw;
    private String articleHtml;
    private UserVO author;
    private LocalDateTime createTime;
    private Long articleStars;
    private Long articleViews;
    private Long articleLikes;

    private List<CategoryPO> categorys;
    private List<TagPO> tags;
}
