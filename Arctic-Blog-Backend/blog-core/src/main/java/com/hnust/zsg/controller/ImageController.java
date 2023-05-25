package com.hnust.zsg.controller;


import com.hnust.zsg.entity.vo.ImageVO;
import com.hnust.zsg.enumeration.ResultCodeType;
import com.hnust.zsg.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@Slf4j
public class ImageController {
    private static final String Access_Token="your_smms_token";

    @PostMapping("/upload")
    public Result<ImageVO> uploadImage(){
     return Result.set(new ImageVO("xxx.png","done","wtf","wtf"), ResultCodeType.SUCCESS);
    }

    @GetMapping("/token")
    public Result<String> getUploadToken(){
        return Result.set(Access_Token, ResultCodeType.SUCCESS);
    }

}
