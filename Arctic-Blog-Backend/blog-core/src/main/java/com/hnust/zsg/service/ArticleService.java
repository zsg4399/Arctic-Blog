package com.hnust.zsg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.zsg.entity.po.ArticleLikeStarPO;
import com.hnust.zsg.entity.po.ArticlePO;
import com.hnust.zsg.entity.vo.ArticleContentVO;
import com.hnust.zsg.entity.vo.ArticleDetailVO;
import com.hnust.zsg.entity.vo.ArticleListVO;

import java.util.Collection;


public interface ArticleService {
    Page<ArticleListVO> getAllArticle(IPage<ArticlePO> page, String order) ;

    ArticleDetailVO findArticleById(Long id);


    void addArticle(ArticleContentVO articleContentVO) throws RuntimeException;

    String deleteArticleById(Long id);

    void likeArticle(Long userId, Long articleId, Boolean islike);

    void starArticle(Long userId, Long articleId, Boolean isStar);

    void insertLikeAndStar();

    Boolean articleLikeStarSaveOrUpdateBatch(Collection<ArticleLikeStarPO> collection);

    void SwipperToRedis();
}
