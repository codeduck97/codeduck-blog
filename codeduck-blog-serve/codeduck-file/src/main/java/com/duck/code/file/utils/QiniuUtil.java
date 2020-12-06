package com.duck.code.file.utils;

import com.duck.code.file.config.system.SysConfig;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @program: codeduck-blog-serve
 * @description: 七牛云上传工具
 * @author: Code Duck
 * @create: 2020-11-06 13:26
 */
@Slf4j
@Component
public class QiniuUtil {

    // 七牛文件上传管理器
    private UploadManager uploadManager;

    // 七牛认证管理
    private Auth auth;

    /**
     * 七牛云上传图片
     *
     * @param multipartFile
     * @return 返回上传后的图片名称
     */
    public String uploadQiniu(MultipartFile multipartFile, String name, SysConfig sysConfig) {
        DefaultPutRet putRet = null;
        try {
            FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
            // 七牛云上传管理器
            uploadManager = new UploadManager(new Configuration(Zone.zone0()));
            // 上传凭证
            auth = Auth.create(sysConfig.getQiniuAccessKey(), sysConfig.getQiniuSecretKey());
            String token = auth.uploadToken(sysConfig.getQiniuBucketName());
            Response response = uploadManager.put(fileInputStream, name, token, null, null);
            if (!response.isOK()) {
                throw new FileUploadIOException(new FileUploadException("七牛云图片上传异常"));
            }
            // 解析上传成功的结果
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("{七牛云图片上传成功 文件名： " + putRet.key + ",七牛图片上传hash: " + putRet.hash + "}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return putRet.key;
    }
}
