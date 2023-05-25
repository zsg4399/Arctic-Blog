package com.hnust.zsg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnust.zsg.entity.po.CategoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CategoryMapper extends BaseMapper<CategoryPO>{
    List<CategoryPO> getAllCategoryByUserId(@Param("id") Long id);

    Long searchCategoryIsExist(@Param("categoryName") String categoryName, @Param("userId") Long userId);


    int insertCategory(CategoryPO categoryPO);

    Integer insertCategoryByArticleId(@Param("articleId") Long articleId, @Param("categoryId") Long categoryId);

    Integer removeArticleCategorys(@Param("id")Long id);

    List<CategoryPO> getAllCategoryByArticleId(@Param("articleId")Long article_id);
}
