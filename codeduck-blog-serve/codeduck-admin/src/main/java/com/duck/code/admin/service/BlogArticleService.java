package com.duck.code.admin.service;

import com.duck.code.admin.vo.BlogArticleVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.pojo.BlogArticle;

import java.util.List;
import java.util.Map;

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
    List<BlogArticleVO>  getBlogList(Long pageNum, Long pageSize);


    /**
     * desc: 判断博文标签下是否存在文章
     * <p>
     *
     * @param tagId
     * @return
     */
    boolean existArticleUnderTag(String tagId);

    /**
     * desc: 该分类id中是否存在文章
     * <p>
     *
     * @param sortId
     * @return
     */
    boolean existArticleUnderSort(String sortId);

    /**
     * desc: 判断是否存在该博文标题
     * <p>
     *
     * @param title
     * @return
     */
    boolean existArticleTitle(String title);

    /**
     * desc: 根据博文标题获取博文信息
     * <p>
     *
     * @param title
     * @return
     */
    List<BlogArticleVO> getBlogByTitle(String title);


}
