package com.duck.code.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.admin.vo.BlogTagVO;
import com.duck.code.commons.entity.blog.BlogTag;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-30
 */

public interface BlogTagService extends IService<BlogTag> {

    /**
     * desc: 分页获取博客标签列表
     * <p>
     *
     * @param pageNum
     * @param pageNum
     * @return
     */
    List<BlogTagVO> getBlogTagList(long pageNum, long pageSize);

    /**
     * desc: 是否存在此标签
     * <p>
     *
     * @param name
     * @return
     */
    boolean existTagName(String name);

    /**
     * desc: 根据标签id，查询在此标签下是否存在博客文章
     * <p>
     *
     * @param id
     * @return
     */
    boolean existBlogArticle(String id);

    /**
     * desc: 更新博客标签
     * <p>
     *
     * @param blogTagVO
     * @return
     */
    boolean updateBlogTag(BlogTagVO blogTagVO);

    /**
     * desc: 根据标签名查询标签信息
     * <p>
     *
     * @param tagName
     * @return
     */
    List<BlogTag> getBlogTagByName(String tagName);

    /**
     * desc: 获取所有的标签信息
     * <p>
     *
     * @param
     * @return
     */
    List<BlogTag> getAllTags();

    /**
     * desc: 递增标签的排序索引
     * <p>
     *
     * @param id
     * @return
     */
    boolean incrIndex(String id);

    /**
     * desc: 重置所有分类的排序值
     * <p>
     *
     * @param
     * @return
     */
    boolean resetIndex();
}
