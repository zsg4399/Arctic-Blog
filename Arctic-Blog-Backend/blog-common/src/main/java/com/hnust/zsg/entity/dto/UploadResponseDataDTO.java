package com.hnust.zsg.entity.dto;

import lombok.Data;

@Data
public class UploadResponseDataDTO {
    private Long file_id;
    private Long width;
    private Long height;
    private String filename;
    private String storename;
    private Long size;
    private String hash;
    private String path;
    private String url;
    private String delete;
    private String page;

    public UploadResponseDataDTO() {
    }

    public UploadResponseDataDTO(Long file_id, Long width, Long height, String filename, String storename, Long size, String hash, String path, String url, String delete, String page) {
        this.file_id = file_id;
        this.width = width;
        this.height = height;
        this.filename = filename;
        this.storename = storename;
        this.size = size;
        this.hash = hash;
        this.path = path;
        this.url = url;
        this.delete = delete;
        this.page = page;
    }
}
