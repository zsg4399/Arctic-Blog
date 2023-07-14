package com.hnust.zsg.entity.dto;

import lombok.Data;

@Data
public class UploadResponeDTO {
    private Boolean success;
    private String code;
    private String message;
    private UploadResponseDataDTO data;

    public UploadResponeDTO() {
    }

    public UploadResponeDTO(Boolean success, String code, String message, UploadResponseDataDTO data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
