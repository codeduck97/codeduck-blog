package com.duck.code.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.admin.vo.BlogSortVO;
import com.duck.code.commons.entity.blog.BlogSort;

import java.util.List;

/**
 * <p>
 * 博客分类表 服务类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-27
 */
public interface BlogSortService extends IService<BlogSort> {

    /**
     * desc: 分页获取博客分类信息，按照包含文章总数排序
     * <p>
     *
     * @param
     * @param pageNum
     * @param pageSize
     * @return BlogSortList
     */
    List<BlogSortVO> getBlogSortList(Long pageNum, Long pageSize);


    /**
     * desc: 更新分类
     * <p>
     *
     * @param blogSortVO
     * @return
     */
    boolean updateBlogSort(BlogSortVO blogSortVO);

    /**
     * desc: 根据分类名查询分类信息
     * <p>
     *
     * @param name
     * @return
     */
    List<BlogSort> getBlogSortByName(String name);

    /**
     * desc: 判断是否存在博客分类标签
     * <p>
     *
     * @param name
     * @return
     */
    boolean existSortName(String name);

    /**
     * desc: 判断该分类id中是否存在博客文章
     * <p>
     *
     * @param id
     * @return
     */
    boolean existBlogArticle(String id);

    /**
     * desc: 获取所有分类集合
     * <p>
     *
     * @param
     * @return
     */
    List<BlogSort> getAllSorts();

    /**
     * desc: 增加排序索引
     * <p>
     *
     * @param
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
