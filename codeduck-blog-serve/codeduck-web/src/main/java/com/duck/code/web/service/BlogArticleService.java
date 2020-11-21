package com.duck.code.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.pojo.BlogArticle;
import com.duck.code.web.vo.BlogArticleVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-20
 */
public interface BlogArticleService extends IService<BlogArticle> {

    /**
     * desc: 获取所有博文（博文按照时间顺序排序返回）
     * <p>
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BlogArticleVO> getAllBlogs(long pageNum, long pageSize);

    /**
     * desc: 获取阅读量最高的五篇文章
     * <p>
     *
     * @param
     * @return
     */
    List<BlogArticle> getHotArticles();
}
