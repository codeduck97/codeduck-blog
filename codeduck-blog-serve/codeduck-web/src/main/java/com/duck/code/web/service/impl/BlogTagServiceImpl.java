package com.duck.code.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.entity.pojo.BlogTag;
import com.duck.code.web.mapper.BlogTagMapper;
import com.duck.code.web.service.BlogArticleService;
import com.duck.code.web.service.BlogTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-30
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {

}
