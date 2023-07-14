package com.hnust.zsg.convert;

import com.hnust.zsg.entity.doc.ArticleEsDOC;
import com.hnust.zsg.entity.po.ArticlePO;
import com.hnust.zsg.entity.vo.ArticleContentVO;
import com.hnust.zsg.entity.vo.ArticleDetailVO;
import com.hnust.zsg.entity.vo.ArticleListVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleConvert {
    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    @Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ArticleListVO POToVo(ArticlePO articlePo);

    ArticlePO ARTICLE_CONTENTVO_TO_ArticlePO(ArticleContentVO articleContentVO);

    ArticleContentVO ArticlePO_TO_Article_ContentVo(ArticlePO articlePO);

    @Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy--MM-dd HH:mm:ss")
    ArticleDetailVO PO_TO_DETAIL_VO(ArticlePO articlePO);

    ArticleEsDOC PO_TO_ESDOC(ArticlePO articlePO);

}
