package com.duck.code.admin.service;

import com.duck.code.commons.entity.pojo.Picture;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: codeduck-blog-serve
 * @description: 七牛云存储服务类
 * @author: Code Duck
 * @create: 2020-11-06 13:21
 **/
public interface QiNiuService {

    /**
     * desc: 上传图片至七牛云端服务器
     * <p>
     *
     * @param multipartFile
     * @return Picture
     */
    Picture uploadFile(MultipartFile multipartFile);
}
