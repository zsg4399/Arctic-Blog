package com.hnust.zsg.controller;


import com.hnust.zsg.entity.vo.ImageVO;
import com.hnust.zsg.enumeration.ResultCodeType;
import com.hnust.zsg.utils.JacksonUtil;
import com.hnust.zsg.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
@RequestMapping("/images")
@Slf4j
public class ImageController {
    private static final String Access_Token="your_smms_token";

    @PostMapping("/upload")
    public Result<ImageVO> uploadImage(StandardMultipartHttpServletRequest request){
     MultipartFile image= request.getFile("smfile");
     return Result.set(new ImageVO("xxx.png","done","wtf","wtf"), ResultCodeType.SUCCESS);
    }


}
