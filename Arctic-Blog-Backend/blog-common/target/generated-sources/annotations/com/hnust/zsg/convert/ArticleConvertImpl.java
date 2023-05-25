package com.hnust.zsg.convert;

import com.hnust.zsg.entity.po.ArticlePO;
import com.hnust.zsg.entity.vo.ArticleContentVO;
import com.hnust.zsg.entity.vo.ArticleDetailVO;
import com.hnust.zsg.entity.vo.ArticleListVO;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-23T23:01:36+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
public class ArticleConvertImpl implements ArticleConvert {

    private final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168 = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );

    @Override
    public ArticleListVO POToVo(ArticlePO articlePo) {
        if ( articlePo == null ) {
            return null;
        }

        ArticleListVO articleListVO = new ArticleListVO();

        if ( articlePo.getCreateTime() != null ) {
            articleListVO.setCreateTime( dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168.format( articlePo.getCreateTime() ) );
        }
        articleListVO.setId( articlePo.getId() );
        articleListVO.setTitle( articlePo.getTitle() );
        articleListVO.setImageUrl( articlePo.getImageUrl() );
        if ( articlePo.getUpdateTime() != null ) {
            articleListVO.setUpdateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( articlePo.getUpdateTime() ) );
        }
        articleListVO.setArticleViews( articlePo.getArticleViews() );
        articleListVO.setArticleLikes( articlePo.getArticleLikes() );
        articleListVO.setArticleStars( articlePo.getArticleStars() );
        articleListVO.setSummary( articlePo.getSummary() );

        return articleListVO;
    }

    @Override
    public ArticlePO ARTICLE_CONTENTVO_TO_ArticlePO(ArticleContentVO articleContentVO) {
        if ( articleContentVO == null ) {
            return null;
        }

        ArticlePO articlePO = new ArticlePO();

        articlePO.setTitle( articleContentVO.getTitle() );
        articlePO.setImageUrl( articleContentVO.getImageUrl() );
        articlePO.setAuthorId( articleContentVO.getAuthorId() );
        articlePO.setSummary( articleContentVO.getSummary() );
        articlePO.setDeleteUrl( articleContentVO.getDeleteUrl() );

        return articlePO;
    }

    @Override
    public ArticleContentVO ArticlePO_TO_Article_ContentVo(ArticlePO articlePO) {
        if ( articlePO == null ) {
            return null;
        }

        ArticleContentVO articleContentVO = new ArticleContentVO();

        articleContentVO.setTitle( articlePO.getTitle() );
        articleContentVO.setImageUrl( articlePO.getImageUrl() );
        articleContentVO.setSummary( articlePO.getSummary() );
        articleContentVO.setAuthorId( articlePO.getAuthorId() );
        articleContentVO.setDeleteUrl( articlePO.getDeleteUrl() );

        return articleContentVO;
    }

    @Override
    public ArticleDetailVO PO_TO_DETAIL_VO(ArticlePO articlePO) {
        if ( articlePO == null ) {
            return null;
        }

        ArticleDetailVO articleDetailVO = new ArticleDetailVO();

        articleDetailVO.setCreateTime( articlePO.getCreateTime() );
        articleDetailVO.setId( articlePO.getId() );
        articleDetailVO.setTitle( articlePO.getTitle() );
        articleDetailVO.setImageUrl( articlePO.getImageUrl() );
        articleDetailVO.setSummary( articlePO.getSummary() );
        articleDetailVO.setArticleStars( articlePO.getArticleStars() );
        articleDetailVO.setArticleViews( articlePO.getArticleViews() );
        articleDetailVO.setArticleLikes( articlePO.getArticleLikes() );

        return articleDetailVO;
    }
}
