package com.hnust.zsg.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorInfoDTO {
    private Integer talks;
    private Integer categorys;
    private Integer articles;
    private Integer tags;
}
