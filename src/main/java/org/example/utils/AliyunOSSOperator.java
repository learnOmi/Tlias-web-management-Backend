package org.example.utils;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.AliyunOSSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
public class AliyunOSSOperator {

    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;

    /**
     * 上传文件到阿里云OSS
     *
     * @param file 上传的文件
     * @return 文件的访问URL
     */
    public String upload(MultipartFile file) {
        // 获取原始文件名和扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";

        // 生成唯一的文件名,避免重名覆盖
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String fileName = dir + '/' + UUID.randomUUID() + extension;

        // 创建凭证提供者, 这里采用使用RAM用户的AK
        CredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();

        // 创建OSSClientBuilder
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(credentialsProvider)
                .region(aliyunOSSProperties.getRegion());

        if (aliyunOSSProperties.getEndpoint() != null) {
            clientBuilder.endpoint(aliyunOSSProperties.getEndpoint());
        }

        try (OSSClient client = clientBuilder.build();
            InputStream inputStream = file.getInputStream()) {

            // 上传文件
            client.putObject(PutObjectRequest.newBuilder()
                            .bucket(aliyunOSSProperties.getBucketName())
                            .key(fileName)
                            .body(BinaryData.fromStream(inputStream, file.getSize()))
                            .build());

            // 构建文件访问URL
            String url = "https://" + aliyunOSSProperties.getBucketName() + "."
                    + aliyunOSSProperties.getEndpoint() + "/" + fileName;

            log.info("文件上传成功, URL: {}", url);
            return url;

        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
        }
    }
}
