package com.duck.code.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.admin.vo.BlogTagVO;
import com.duck.code.commons.entity.pojo.BlogTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-30
 */
public interface BlogTagMapper extends BaseMapper<BlogTag> {

    List<BlogTagVO> queryAllByPageInfo(@Param("pageNum") Long pageNum, @Param("pageSize")  Long pageSize);

}
