package com.hnust.zsg.entity.vo;

import com.hnust.zsg.entity.doc.ArticleEsDOC;

import java.util.List;

public class ArticleSearchVO {
    private Long total;
    private List<ArticleEsDOC> articles;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<ArticleEsDOC> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleEsDOC> articles) {
        this.articles = articles;
    }

    public ArticleSearchVO() {
    }

    public ArticleSearchVO(Long total, List<ArticleEsDOC> articles) {
        this.total = total;
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "ArticleSearchVO{" +
                "total=" + total +
                ", articles=" + articles +
                '}';
    }
}
