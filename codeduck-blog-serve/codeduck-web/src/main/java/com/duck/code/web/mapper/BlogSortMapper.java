package com.duck.code.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.commons.entity.pojo.BlogSort;
import com.duck.code.commons.entity.pojo.BlogTag;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 * 博客分类表 Mapper 接口
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-27
 */
public interface BlogSortMapper extends BaseMapper<BlogSort> {

    /**
     * desc: 查询分类集合（每个分类中存在博文）
     * <p>
     *
     * @param
     * @return
     */
    @Select("SELECT * FROM tb_blog_sort AS s LEFT JOIN tb_blog AS b ON s.`id`=b.`sort_id` WHERE s.`deleted`=0 AND b.`deleted`=0 GROUP BY s.`id`")
    List<BlogSort> queryAllSortsByBlogId();
}
