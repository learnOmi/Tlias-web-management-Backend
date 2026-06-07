package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.example.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@Slf4j
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传: {}", file.getOriginalFilename());

        // 保存到本地磁盘
//        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
//        file.transferTo(new File("D:/images/" + newFileName));

        // 上传到阿里云OSS
        String url = aliyunOSSOperator.upload(file);
        log.info("文件上传OSS, url: {}", url);

        return Result.success(url);
    }
}
