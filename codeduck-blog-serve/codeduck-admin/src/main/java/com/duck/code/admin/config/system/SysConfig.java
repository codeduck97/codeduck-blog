package com.duck.code.admin.config.system;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @program: codeduck-blog-serve
 * @description: 系统配置文件信息类
 * @author: Code Duck
 * @create: 2020-11-04 14:13
 */
@Data
@Configuration
public class SysConfig {
    /**
     * 七牛域名domain
     */
    @Value("${oss.qiniu.domain}")
    private String qiniuDomain;
    /**
     * 七牛ACCESS_KEY
     */
    @Value("${oss.qiniu.accessKey}")
    private String qiniuAccessKey;
    /**
     * 七牛SECRET_KEY
     */
    @Value("${oss.qiniu.secretKey}")
    private String qiniuSecretKey;
    /**
     * 七牛空间名
     */
    @Value("${oss.qiniu.bucketName}")
    private String qiniuBucketName;

    @Value("${file.upload.path}")
    private String localDir;
}
