package com.duck.code.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.admin.vo.BlogSortVO;
import com.duck.code.admin.mapper.BlogSortMapper;
import com.duck.code.admin.service.BlogArticleService;
import com.duck.code.admin.service.BlogSortService;
import com.duck.code.admin.vo.BlogTagVO;
import com.duck.code.commons.entity.pojo.BlogSort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 博客分类表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-27
 */
@Service
@Slf4j
public class BlogSortServiceImpl extends ServiceImpl<BlogSortMapper, BlogSort> implements BlogSortService {

    @Resource
    private BlogArticleService blogArticleService;

    /**
     * desc: 分页获取博客分类信息，按照包含文章总数排序
     * <p>
     *
     * @param
     * @param pageNum
     * @param pageSize
     * @return BlogSortList
     */
    @Override
    public List<BlogSortVO> getBlogSortList(Long pageNum, Long pageSize) {
        long start;
        pageNum = pageNum - 1;
        if (pageNum == 0) {
            start = pageNum;
        } else {
            start = pageNum * pageSize;
        }

        List<BlogSortVO> list = this.baseMapper.queryTotalNumOfArticlesInCategory(start, pageSize);
        list.forEach(i -> {
            if (i.getArticlesNum() == null) {
                i.setArticlesNum(0);
            }
        });
        return list;
    }


    /**
     * desc: 更新分类
     * <p>
     *
     * @param blogSortVO
     * @return
     */
    @Override
    public boolean updateBlogSort(BlogSortVO blogSortVO) {
        BlogSort blogSort = new BlogSort();
        blogSort.setId(blogSortVO.getId());
        blogSort.setSortIndex(blogSortVO.getSortIndex());
        blogSort.setSortName(blogSortVO.getSortName());
        blogSort.setUpdateTime(LocalDateTime.now());
        return super.updateById(blogSort);
    }

    /**
     * desc: 根据分类名查询分类信息
     * <p>
     *
     * @param name
     * @return
     */
    @Override
    public List<BlogSort> getBlogSortByName(String name) {
        QueryWrapper<BlogSort> wrapper = new QueryWrapper<>();
        wrapper.eq("sort_name", name);
        return this.baseMapper.selectList(wrapper);
    }

    /**
     * desc: 判断是否存在博客分类标签
     * <p>
     *
     * @param name
     * @return
     */
    @Override
    public boolean existSortName(String name) {
        BlogSort blogSort = super.getOne(Wrappers.<BlogSort>query().eq("sort_name", name));
        if (blogSort == null) {
            return false;
        }
        return true;
    }

    /**
     * desc: 判断该分类id中是否存在博客文章
     * <p>
     *
     * @param sortId
     * @return
     */
    @Override
    public boolean existBlogArticle(String sortId) {
        return blogArticleService.existArticleUnderSort(sortId);
    }

    /**
     * desc: 获取所有分类集合
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public List<BlogSort> getAllSorts() {
        return this.baseMapper.queryAllSorts();
    }


    /**
     * desc: 增加排序索引
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public boolean incrIndex(String id) {
        BlogSort max = this.baseMapper.queryMaxIndexOfSort();
        BlogSort blogSort = super.getById(id);
        Integer sortIndex = max.getSortIndex();
        blogSort.setSortIndex(++sortIndex);
        return super.updateById(blogSort);
    }

    /**
     * desc: 重置所有分类的排序值
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public boolean resetIndex() {
        UpdateWrapper<BlogSort> wrapper = new UpdateWrapper<>();
        wrapper.set(true, "sort_index", 0);
        return super.update(wrapper);
    }
}
