package com.duck.code.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.entity.pojo.BlogSort;
import com.duck.code.web.mapper.BlogSortMapper;
import com.duck.code.web.service.BlogSortService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客分类表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-27
 */
@Service
public class BlogSortServiceImpl extends ServiceImpl<BlogSortMapper, BlogSort> implements BlogSortService {

}
