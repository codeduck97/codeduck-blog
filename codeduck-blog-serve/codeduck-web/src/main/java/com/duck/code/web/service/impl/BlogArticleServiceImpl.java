package com.duck.code.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.entity.pojo.BlogArticle;
import com.duck.code.web.mapper.BlogArticleMapper;
import com.duck.code.web.service.BlogArticleService;
import com.duck.code.web.service.BlogSortService;
import com.duck.code.web.service.BlogTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-20
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {

}
