package com.hnust.zsg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnust.zsg.entity.po.ArticleLikeStarPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleLikeStarMapper  {
    int insertOrUpdate(ArticleLikeStarPO articleLikeStarPO);
    Long getIdByArticleIdAndUserId(@Param("articleId")Long articleId,@Param("userId")Long userId);
}
