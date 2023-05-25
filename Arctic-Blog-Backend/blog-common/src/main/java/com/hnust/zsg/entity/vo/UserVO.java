package com.hnust.zsg.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {
    public UserVO(){}
    private Long id;
    private String username;
    private String avatar;
}
