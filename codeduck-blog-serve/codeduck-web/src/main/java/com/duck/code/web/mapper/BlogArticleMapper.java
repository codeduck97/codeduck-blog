package com.duck.code.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.commons.entity.pojo.BlogArticle;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-20
 */
public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

    /**
     * desc: 查询点击率最高的五篇文章
     * <p>
     *
     * @param
     * @return
     */
    @Select("SELECT * FROM tb_blog AS b WHERE b.`deleted`=0 ORDER BY b.`hits` DESC LIMIT 0,5")
    List<BlogArticle> queryHotArticles();
}
