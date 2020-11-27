package com.duck.code.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.blog.BlogArticle;
import com.duck.code.commons.entity.blog.BlogSort;

import java.util.List;
import java.util.Map;

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
     * desc: 获取分类信息的字典集合
     * <p>
     *
     * @param
     * @return key=sortId，value=sortName
     */
    Map<String,String> getSortDic();

    /**
     * desc: 获取分类列表
     * <p>
     *
     * @param
     * @return
     */
    List<BlogSort> getSortList();

    /**
     * desc: 通过sortId获取相关博文列表
     * <p>
     *
     * @param id
     * @return
     */
    List<BlogArticle> getArticlesBySortId(String id);
}
