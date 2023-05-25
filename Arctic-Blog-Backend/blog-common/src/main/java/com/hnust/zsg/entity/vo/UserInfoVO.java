package com.hnust.zsg.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    private Long id;
    private String username;
    private Boolean enabled;
    private String avatar;
    private String[] address;
    private String birthday;
    private String description;
    private String sex;


}
