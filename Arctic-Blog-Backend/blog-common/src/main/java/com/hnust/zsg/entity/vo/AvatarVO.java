package com.hnust.zsg.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvatarVO {
    private String username;
    private String avatar;
    private Long stars;
    private Long fans;
    private Long likes;
}
