package com.hnust.zsg.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("user")
public class UserPO implements Serializable {
    public static final long serialVersionUID = 778L;
    @TableId(value = "id")
    private Long id;
    @TableField(value = "username")
    private String username;
    @TableField(value = "password")
    private String password;
    @TableField(value = "email")
    private String email;
    @TableField(value = "enabled")
    private Boolean enabled;
    @TableField(value = "avatar")
    private String avatar;
    @TableField("address")
    private String address;
    @TableField("birthday")
    private LocalDate birthday;
    @TableField("description")
    private String description;
    @TableField("sex")
    private String sex;
    @TableField("phone")
    private String phone;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}

