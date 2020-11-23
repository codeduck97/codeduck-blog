package com.duck.code.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.pojo.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 图片信息表 服务类
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-20
 */
public interface PictureService extends IService<Picture> {
    /**
     * desc: 批量上传图片至本地服务器
     * <p>
     *
     * @param multipartFileList
     * @return
     */
    Picture batchUploadPicToServe(MultipartFile multipartFileList);

    /**
     * desc: 批量上传图片至七牛云存储
     * <p>
     *
     * @param picture
     * @return
     */
    Picture batchUploadPicToQiNiu(MultipartFile picture);

    /**
     * desc: 分页获取图片信息
     * <p>
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Picture> getPictureList(long pageNum, long pageSize);
}
