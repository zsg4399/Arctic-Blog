package com.hnust.zsg.controller;

import com.hnust.zsg.entity.vo.UserCategoryVO;
import com.hnust.zsg.enumeration.ResultCodeType;
import com.hnust.zsg.service.CategoryService;
import com.hnust.zsg.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/categorys")
@RestController
public class CategoryController {
    @Autowired
    @Lazy
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public Result<UserCategoryVO> getAllCategoryByUserId(@PathVariable("id") Long id){
        UserCategoryVO categoryVO= categoryService.getAllCategoryByUserId(id);
        return Result.set(categoryVO, ResultCodeType.SUCCESS);
    }
}
