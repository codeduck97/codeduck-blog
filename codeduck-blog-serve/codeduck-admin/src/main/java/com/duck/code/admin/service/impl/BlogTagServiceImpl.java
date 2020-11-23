package com.duck.code.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.admin.entity.BlogTagVO;
import com.duck.code.admin.mapper.BlogTagMapper;
import com.duck.code.admin.service.BlogArticleService;
import com.duck.code.admin.service.BlogTagService;
import com.duck.code.commons.entity.pojo.BlogTag;
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

    @Resource
    private BlogArticleService blogArticleService;

    /**
     * desc: 分页获取博客标签列表
     * <p>
     *
     * @param pageNum
     * @param pageNum
     * @return
     */
    @Override
    public List<BlogTagVO> getBlogTagList(long pageNum, long pageSize) {

        return this.baseMapper.queryAllByPageInfo((pageNum - 1) * pageSize, pageSize);
    }

    /**
     * desc: 是否存在此标签
     * <p>
     *
     * @param name
     * @return
     */
    @Override
    public boolean existTagName(String name) {
        QueryWrapper<BlogTag> wrapper = new QueryWrapper();
        wrapper.eq("tag_name", name);
        if (super.getOne(wrapper) == null) {
            return false;
        }
        return true;
    }

    /**
     * desc: 根据标签id，查询在此标签下是否存在博客文章
     * <p>
     *
     * @param id
     * @return
     */
    @Override
    public boolean existBlogArticle(String id) {
        return blogArticleService.existArticleUnderTag(id);
    }

    /**
     * desc: 更新博客标签
     * <p>
     *
     * @param blogTagVO
     * @return
     */
    @Override
    public boolean updateBlogTag(BlogTagVO blogTagVO) {
        // 主要更新三个部分，标签名，标签排序，标签更新时间
        BlogTag blogTag = new BlogTag();
        blogTag.setId(blogTagVO.getId());
        blogTag.setTagIndex(blogTagVO.getTagIndex());
        blogTag.setTagName(blogTagVO.getTagName());
        blogTag.setUpdateTime(LocalDateTime.now());
        return super.updateById(blogTag);
    }

    /**
     * desc: 根据标签名查询标签信息
     * <p>
     *
     * @param tagName
     * @return
     */
    @Override
    public List<BlogTag> getBlogTagByName(String tagName) {
        QueryWrapper<BlogTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_name", tagName);
        return this.baseMapper.selectList(wrapper);
    }

    /**
     * desc: 获取所有的标签信息
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public List<BlogTagVO> getAllTags() {
        final long pageNum = 0;
        final long pageSize = 999;
        List<BlogTagVO> list = this.baseMapper.queryAllByPageInfo(pageNum, pageSize);
        return list;
    }
}
