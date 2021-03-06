package com.duck.code.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.blog.BlogArticle;
import com.duck.code.commons.entity.blog.BlogTag;

import java.util.List;
import java.util.Map;

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
     * desc: 获取标签的字典集合
     * <p>
     *
     * @param
     * @return key=tagId，value=tagName
     */
    Map<String,String> getTagDic();

    /**
     * desc: 获取标签云信息
     * <p>
     *
     * @param
     * @return
     */
    List<BlogTag> getTagCloud();

    /**
     * desc: 通过标签id获取存在的博文列表
     * <p>
     *
     * @param id
     * @return
     */
    List<BlogArticle> getArticlesByTagId(String id);

    /**
     * desc: 获取存在博文的标签列表
     * <p>
     *
     * @param
     * @return
     */
    List<BlogTag> getTagList();
}
