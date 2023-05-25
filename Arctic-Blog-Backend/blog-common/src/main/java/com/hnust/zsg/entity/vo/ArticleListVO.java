package com.hnust.zsg.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hnust.zsg.entity.po.CategoryPO;
import com.hnust.zsg.entity.po.TagPO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author 86187
 */
@Data
@AllArgsConstructor
public class ArticleListVO implements Serializable{
    public ArticleListVO(){}

    private static final long serialVersionUID=9992428L;
    private Long id;
    private String title;
    private String imageUrl;
    private String createTime;
    private String updateTime;
    private Long articleViews;
    private Long articleLikes;
    private Long articleStars;
    private String authorName;
    private String summary;

    private Long commentAmount;

}
