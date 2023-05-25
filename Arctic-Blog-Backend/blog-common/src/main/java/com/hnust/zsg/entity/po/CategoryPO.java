package com.hnust.zsg.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("category")
public class CategoryPO {

  @TableId("id")
  private Long id;

  @TableField("categoryName")
  private String categoryName;

  @TableField("categoryImg")
  private String categoryImg;

  @TableField("userId")
  private Long userId;
  public CategoryPO(){};
}
