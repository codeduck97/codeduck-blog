package com.duck.code.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.admin.mapper.PictureMapper;
import com.duck.code.admin.service.PictureService;
import com.duck.code.commons.entity.pojo.Picture;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图片信息表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-20
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

}
