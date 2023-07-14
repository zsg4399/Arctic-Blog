package com.hnust.zsg;

import com.hnust.zsg.entity.doc.ArticleEsDOC;
import com.hnust.zsg.mapper.ArticleMapper;
import com.hnust.zsg.utils.ElasticSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;


@SpringBootTest
@Slf4j
class MyBlogApplicationTests {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ElasticSearchUtil util;


    @Test
    void testSQL() throws Exception {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        String message = "vue";

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("summary")
                .field("content")
                .field("title")
                //设置关键字只读取50字的上下文
                .fragmentSize(50)
                //设置某个字段未匹配到的情况下，返回的最大字数，初始化默认值是0
                .noMatchSize(50)
                //设置最多只读取一条关键字
                .numOfFragments(1)
                .preTags("<span>")
                .postTags("</span>");

        builder.withHighlightBuilder(highlightBuilder);
        builder.withPageable(PageRequest.of(0, 10));
        //构建查询条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery(message, "summary", "content", "title"));
        builder.withQuery(boolQuery);

        SearchHits<ArticleEsDOC> result = util.search(builder.build(), ArticleEsDOC.class);
        result.get().forEach(e -> {
            e.getHighlightFields().forEach((k, v) -> System.out.println(k + ":" + v));

        });


    }

    @Test
    void search() {
        String msg = "vite";
        CriteriaQuery query = new CriteriaQuery(Criteria.where("content").contains(msg)
                .or("title").contains(msg)
                .or("summary").contains(msg
                ));
    }
}
