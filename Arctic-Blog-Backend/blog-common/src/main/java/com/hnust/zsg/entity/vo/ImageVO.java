package com.hnust.zsg.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageVO {
    private String name;
    private String status;
    private String url;
    private String thumbUrl;
}
