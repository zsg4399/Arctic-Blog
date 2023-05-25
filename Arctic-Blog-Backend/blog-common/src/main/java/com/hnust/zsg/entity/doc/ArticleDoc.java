package com.hnust.zsg.entity.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @author 86187
 */
@Data
@AllArgsConstructor
@Document("article_content")
public class ArticleDoc {
     @MongoId
     private Long id;
     private String article_raw;
     private String article_html;

     public ArticleDoc(){};
}
