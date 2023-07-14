package com.hnust.zsg.entity.doc;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author 86187
 */
@Document(indexName = "arctic-article")
public class ArticleEsDOC implements Serializable {
    private static final long seriaVersionUID=-87871313113L;
    @Id
    private Long id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;
    @Field(type = FieldType.Date)
    private String createTime;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArticleEsDOC(){}

    public ArticleEsDOC(Long id, String title, String content, String createTime, String summary) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "ArticleEsDoc{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
