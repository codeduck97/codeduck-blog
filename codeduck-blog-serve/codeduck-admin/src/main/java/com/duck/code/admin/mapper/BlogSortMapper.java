package com.duck.code.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.admin.entity.BlogSortVO;
import com.duck.code.commons.entity.pojo.BlogSort;
import org.apache.ibatis.annotations.Param;

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
     * desc: 分页获取分类数据
     * <p>
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BlogSortVO> queryAllByPageInfo(@Param("pageNum") Long pageNum, @Param("pageSize")  Long pageSize);
}
