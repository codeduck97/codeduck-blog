package com.duck.code.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.duck.code.admin.config.system.SysConfig;
import com.duck.code.admin.service.PictureService;
import com.duck.code.admin.service.QiNiuService;
import com.duck.code.admin.utils.FileUtil;
import com.duck.code.admin.utils.PictureUtil;
import com.duck.code.admin.utils.QiniuUtil;
import com.duck.code.commons.entity.pojo.Picture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2020-11-06 13:24
 */
@Service
@Slf4j
public class QiNiuServiceImpl implements QiNiuService {

    @Resource
    private SysConfig sysConfig;

    @Resource
    private PictureService pictureService;

    @Resource
    private QiniuUtil qiniuUtil;

    /**
     * desc: 上传图片至七牛云端服务器
     * <p>
     *
     * @param multipartFile
     * @return Picture
     */
    @Override
    public Picture uploadFile(MultipartFile multipartFile) {

        if (multipartFile!=null && multipartFile.getSize()>0) {
            String originalName = multipartFile.getOriginalFilename();
            String suffix = FileUtil.getSuffix(originalName);
            if (PictureUtil.isPic(suffix)) {
                try {
                    // 图片别名
                    String alias = FileUtil.generateName();
                    // 随机生成唯一文件名
                    String currentName = FileUtil.createFileName(suffix);
                    // 获取图片分辨率
                    BufferedImage image = ImageIO.read(FileUtil.multipartFileToFile(multipartFile));
                    Integer height = image.getHeight();
                    Integer width = image.getWidth();
                    String resolution = height + "x" + width;
                    // 获取图片大小
                    Long size = multipartFile.getSize();
                    // 使用七牛云上传工具上传图片，返回云端存储的图片名称
                    currentName = qiniuUtil.uploadQiniu(multipartFile,currentName,sysConfig);
                    // 七牛云链接地址
                    String qiNiuUrl = sysConfig.getQiniuDomain() + "/" + currentName;
                    // markdown链接地址
                    String mdUrl = PictureUtil.generateMdUrl(qiNiuUrl);


                    Picture picture = new Picture();
                    picture.setAlias(alias);
                    picture.setLocalUrl(qiNiuUrl);
                    picture.setQiNiuUrl(qiNiuUrl);
                    picture.setMdUrl(mdUrl);
                    picture.setSuffixName(suffix);
                    picture.setOriginalName(originalName);
                    picture.setPicName(currentName);
                    picture.setResolution(resolution);
                    picture.setPicSize(size.intValue());
                    if (pictureService.save(picture)) {
                        return pictureService.getOne(Wrappers.<Picture>query().eq("pic_name", currentName));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
