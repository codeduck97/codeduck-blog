package com.duck.code.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.admin.vo.BlogSortVO;
import com.duck.code.commons.entity.blog.BlogSort;
import org.apache.ibatis.annotations.Param;
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
     * desc: 获取每个类别下的文章总数
     * <p>
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BlogSortVO> queryTotalNumOfArticlesInCategory(@Param("pageNum") Long pageNum, @Param("pageSize")  Long pageSize);

    /**
     * desc: 查询所有的分类信息
     * <p>
     *
     * @param
     * @return
     */
    @Select("select * from tb_blog_sort where deleted=0")
    List<BlogSort> queryAllSorts();

    /**
     * desc: 查询排序值最大的一个分类信息
     * <p>
     *
     * @param
     * @return
     */
    @Select("SELECT * FROM tb_blog_sort AS s WHERE s.`deleted`=0 ORDER BY s.`sort_index` DESC LIMIT 0,1")
    BlogSort queryMaxIndexOfSort();
}
