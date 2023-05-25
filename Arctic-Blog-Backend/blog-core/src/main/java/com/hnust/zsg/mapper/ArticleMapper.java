package com.hnust.zsg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnust.zsg.entity.dto.SwiperVO;
import com.hnust.zsg.entity.po.ArticlePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ArticleMapper extends BaseMapper<ArticlePO>{
    Page<Long> getAllArticleId(IPage<ArticlePO> page,@Param("order") String order);
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    Long addArticle(ArticlePO articlePO);

    String getDeleteUrlById(@Param("id") Long id);
    ArticlePO getArticleById(@Param("id") Long id);

    List<SwiperVO> getSwipperPicture();
}
