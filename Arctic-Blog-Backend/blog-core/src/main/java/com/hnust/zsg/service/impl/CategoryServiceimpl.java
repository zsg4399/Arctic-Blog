package com.hnust.zsg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.zsg.entity.po.CategoryPO;
import com.hnust.zsg.entity.vo.UserCategoryVO;
import com.hnust.zsg.mapper.CategoryMapper;
import com.hnust.zsg.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceimpl extends ServiceImpl<CategoryMapper, CategoryPO> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public UserCategoryVO getAllCategoryByUserId(Long id) {

        UserCategoryVO categoryVO=new UserCategoryVO();
        categoryVO.setCategorys(categoryMapper.getAllCategoryByUserId(id));
        categoryVO.setUserId(id);
        return categoryVO;
    }

    @Override
    public void insertCategorysByArticleId(Long articleId, List<CategoryPO> categorys,Long userId) {
        for (CategoryPO category : categorys) {
        Long categoryId=categoryMapper.searchCategoryIsExist(category.getCategoryName(),userId);
            if(categoryId==null){
             CategoryPO categoryPO=new CategoryPO(null,category.getCategoryName(),category.getCategoryImg(),userId);
             categoryMapper.insertCategory(categoryPO);
             categoryId=categoryPO.getId();
             categoryMapper.insertCategoryByArticleId(articleId,categoryId);
         }

        }
    }

    @Override
    public Integer removeArticleCategorys(Long id) {
        return categoryMapper.removeArticleCategorys(id);
    }


    @Override
    public List<CategoryPO> getAllCategoryByArticleId(Long id){
        return categoryMapper.getAllCategoryByArticleId(id);
    }
}
