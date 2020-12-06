package com.duck.code.file.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.picture.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 图片信息表 服务类
 * </p>
 *
 * @author Code Duck
 * @since 2020-12-05
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
     * desc: 分页获取图片信息
     * <p>
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<Picture> getPictureList(long pageNum, long pageSize);

}
