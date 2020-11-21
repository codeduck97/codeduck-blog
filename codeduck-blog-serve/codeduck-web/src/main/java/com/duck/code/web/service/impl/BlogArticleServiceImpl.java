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
import com.duck.code.web.vo.BlogArticleVO;
import com.duck.code.web.vo.BlogSortVO;
import com.duck.code.web.vo.BlogTagVO;
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

    @Resource
    private BlogSortService blogSortService;

    @Resource
    private BlogTagService blogTagService;

    @Override
    public List<BlogArticleVO> getAllBlogs(long pageNum, long pageSize) {
        // 获取标签表字典
        Map<String, String> sortDic = blogSortService.getSortDic();
        // 获取分类表字典
        Map<String, String> tagDic = blogTagService.getTagDic();

        Page<BlogArticle> page = new Page<>(pageNum, pageSize);
        Page<BlogArticle> blogPage = super.page(page,
                Wrappers.<BlogArticle>query().orderByDesc("creation_time"));
        List<BlogArticle> articles = blogPage.getRecords();

        // 整合标签信息、分类信息、博文列表集合到BlogArticleVO中
        List<BlogArticleVO> blogArticleVOList = new ArrayList<>();
        articles.forEach(article -> {
            BlogArticleVO blogArticleVO = new BlogArticleVO();
            BeanUtil.copyProperties(article, blogArticleVO, "tag", "sort");
            blogArticleVO.setSort(sortDic.get(article.getSortId()));
            blogArticleVO.setTag(tagDic.get(article.getTagId()));
            blogArticleVOList.add(blogArticleVO);
        });
        return blogArticleVOList;
    }

    /**
     * desc: 获取阅读量最高的五篇文章
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public List<BlogArticle> getHotArticles() {
        return this.baseMapper.queryHotArticles();
    }
}
