package com.duck.code.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.admin.config.system.SysConfig;
import com.duck.code.admin.mapper.PictureMapper;
import com.duck.code.admin.service.PictureService;
import com.duck.code.admin.utils.FileUtil;
import com.duck.code.admin.utils.PictureUtil;
import com.duck.code.commons.entity.picture.Picture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 图片信息表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-20
 */
@Service
@Slf4j
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    @Resource
    private SysConfig sysConfig;

    /**
     * desc: 批量上传图片
     * <p>
     *
     * @param pic
     * @return
     */
    @Override
    public Picture batchUploadPicToServe(MultipartFile pic) {

        String localDir = sysConfig.getLocalDir();
        Picture resPic = null;
        if (pic != null && pic.getSize() > 0) {
            // 获取图片的原名称
            String originalName = pic.getOriginalFilename();
            // 获取并判断每一个文件后缀是否合法
            String suffix = PictureUtil.getSuffix(originalName);
            if (PictureUtil.isPic(suffix)) {
                try {
                    Picture picture = new Picture();
                    Long size = pic.getSize();
                    BufferedImage image = ImageIO.read(FileUtil.multipartFileToFile(pic));
                    Integer height = image.getHeight();
                    Integer width = image.getWidth();

                    // 图片别名：用于前端展示和检索
                    String alias = FileUtil.generateName();
                    // 图片存储目录
                    String storageDir = FileUtil.createDir();
                    // 图片新名称
                    String picName = FileUtil.createFileName(suffix);
                    // 图片服务器Url
                    String picUrl = PictureUtil.generatePicUrl(storageDir, picName);
                    // 图片markdown Url
                    String mdUrl = PictureUtil.generateMdUrl(storageDir, picName);


                    picture.setAlias(alias);
                    picture.setPicName(picName);
                    picture.setOriginalName(originalName);
                    picture.setSuffixName(suffix);
                    picture.setPicSize(size.intValue());
                    picture.setResolution(height.toString() + "x" + width.toString());
                    picture.setLocalUrl(picUrl);
                    picture.setMdUrl(mdUrl);

                    /* 先将图片写入硬盘，然后存入数据库 */
                    // 图片将要存储的服务器目录: 配置文件的服务器根路径+自动生成的目录
                    String targetDir = localDir + "/" + storageDir;
                    File file = new File(targetDir);
                    if (!file.exists()) file.mkdirs();

                    File saveFile = new File(targetDir + "/" + picName);
                    saveFile.createNewFile();
                    // 将上传文件写入硬盘
                    pic.transferTo(saveFile);

                    // 保存文件到数据库
                    if (super.save(picture)) {
                        resPic = super.getOne(Wrappers.<Picture>query().eq("pic_name", picName));
                        log.info("本地服务器图片上传成功{{}}",resPic);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resPic;
    }

    /**
     * desc: 批量上传图片至七牛云存储
     * <p>
     *
     * @param picture
     * @return
     */
    @Override
    public Picture batchUploadPicToQiNiu(MultipartFile picture) {
        return null;
    }

    /**
     * desc: 分页获取图片信息
     * <p>
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Picture> getPictureList(long pageNum, long pageSize) {
        Page<Picture> pageInfo = new Page<>(pageNum, pageSize);
        Page<Picture> page = super.page(pageInfo, Wrappers.<Picture>query().orderByDesc("creation_time"));
        return page.getRecords();
    }
}
