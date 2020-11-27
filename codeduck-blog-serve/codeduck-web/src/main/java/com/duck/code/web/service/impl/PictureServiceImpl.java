package com.duck.code.web.service.impl;

import com.duck.code.commons.entity.picture.Picture;
import com.duck.code.web.mapper.PictureMapper;
import com.duck.code.web.service.PictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
