package com.hnust.zsg.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalkPO {
    @TableId("id")
    private Long id;

    @TableField("content")
    private String content;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Timestamp createTime;

    @TableField("likes")
    private Long likes;
}
