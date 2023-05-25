package com.hnust.zsg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.zsg.entity.po.CategoryPO;
import com.hnust.zsg.entity.vo.UserCategoryVO;

import java.util.List;

public interface CategoryService extends IService<CategoryPO> {
    UserCategoryVO getAllCategoryByUserId(Long id);

    void insertCategorysByArticleId(Long articleId, List<CategoryPO> categorys, Long userId);

    Integer removeArticleCategorys(Long id);

    List<CategoryPO> getAllCategoryByArticleId(Long id);
}
