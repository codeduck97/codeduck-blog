package com.duck.code.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.admin.vo.BlogArticleVO;
import com.duck.code.admin.vo.BlogSortVO;
import com.duck.code.admin.vo.BlogTagVO;
import com.duck.code.admin.mapper.BlogArticleMapper;
import com.duck.code.admin.service.BlogArticleService;
import com.duck.code.admin.service.BlogSortService;
import com.duck.code.admin.service.BlogTagService;
import com.duck.code.commons.entity.pojo.BlogArticle;
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
    private BlogArticleService blogArticleService;

    @Resource
    private BlogSortService blogSortService;

    @Resource
    private BlogTagService blogTagService;

    /**
     * desc: 获取所有博文（博文按照时间顺序排序返回）
     * <p>
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<BlogArticleVO> getBlogList(Long pageNum, Long pageSize) {

        // 获取标签表的所有信息
        List<BlogSortVO> sorts = blogSortService.getAllSorts();
        HashMap<String, BlogSortVO> sortMap = new HashMap<>();
        // 创建字典集合保存所有标签信息
        sorts.forEach(sort -> sortMap.put(sort.getId(), sort));

        // 获取分类表的所有信息
        List<BlogTagVO> tags = blogTagService.getAllTags();
        Map<String, BlogTagVO> tagMap = new HashMap<>();
        // 创建字典集合保存所有分类信息
        tags.forEach(tag -> tagMap.put(tag.getId(), tag));

        Page<BlogArticle> page = new Page<>(pageNum, pageSize);
        Page<BlogArticle> blogPage = super.page(page,
                Wrappers.<BlogArticle>query().orderByDesc("creation_time"));
        List<BlogArticle> articles = blogPage.getRecords();

        // 整合标签信息、分类信息、博文列表集合到BlogArticleVO中
        List<BlogArticleVO> blogArticleVOList = new ArrayList<>();
        articles.forEach(article -> {
            BlogArticleVO blogArticleVO = new BlogArticleVO();
            BeanUtil.copyProperties(article, blogArticleVO, "tagId", "sortId");
            blogArticleVO.setSort(sortMap.get(article.getSortId()));
            blogArticleVO.setTag(tagMap.get(article.getTagId()));
            blogArticleVOList.add(blogArticleVO);
        });
        return blogArticleVOList;
    }

    /**
     * desc: 判断博文标签下是否存在文章
     * <p>
     *
     * @param tagId
     * @return
     */
    @Override
    public boolean existArticleUnderTag(String tagId) {
        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id", tagId);
        Integer count = this.baseMapper.selectCount(wrapper);
        if (count <= 0) {
            return false;
        }
        return true;
    }

    /**
     * desc: 该分类id中是否存在文章
     * <p>
     *
     * @param sortId
     * @return
     */
    @Override
    public boolean existArticleUnderSort(String sortId) {
        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("sort_id", sortId);
        Integer count = this.baseMapper.selectCount(wrapper);
        if (count <= 0) {
            return false;
        }
        return true;
    }

    /**
     * desc: 判断是否存在该博文标题
     * <p>
     *
     * @param title
     * @return
     */
    @Override
    public boolean existArticleTitle(String title) {
        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        BlogArticle blogArticle = super.getOne(wrapper);
        if (blogArticle == null) {
            return false;
        }
        return true;
    }

    /**
     * desc: 根据博文标题获取博文信息
     * <p>
     *
     * @param title
     * @return
     */
    @Override
    public List<BlogArticleVO> getBlogByTitle(String title) {

        // 获取标签表的所有信息
        List<BlogSortVO> sorts = blogSortService.getAllSorts();
        HashMap<String, BlogSortVO> sortMap = new HashMap<>();
        // 创建字典集合保存所有标签信息
        sorts.forEach(sort -> sortMap.put(sort.getId(), sort));

        // 获取分类表的所有信息
        List<BlogTagVO> tags = blogTagService.getAllTags();
        Map<String, BlogTagVO> tagMap = new HashMap<>();
        // 创建字典集合保存所有分类信息
        tags.forEach(tag -> tagMap.put(tag.getId(), tag));

        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        List<BlogArticle> articles = this.baseMapper.selectList(wrapper);

        // 整合标签信息、分类信息、博文列表集合到BlogArticleVO中
        List<BlogArticleVO> blogArticleVOList = new ArrayList<>();
        if (!articles.isEmpty()) {
            articles.forEach(article -> {
                BlogArticleVO blogArticleVO = new BlogArticleVO();
                BeanUtil.copyProperties(article, blogArticleVO, "tagId", "sortId");
                blogArticleVO.setSort(sortMap.get(article.getSortId()));
                blogArticleVO.setTag(tagMap.get(article.getTagId()));
                blogArticleVOList.add(blogArticleVO);
            });
        }
        return blogArticleVOList;
    }
}
