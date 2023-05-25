package com.hnust.zsg.entity.vo;

import com.hnust.zsg.entity.po.CategoryPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCategoryVO {
    private Long userId;
    private List<CategoryPO> categorys;
}
