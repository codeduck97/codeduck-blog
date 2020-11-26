package com.duck.code.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.admin.vo.BlogTagVO;
import com.duck.code.commons.entity.pojo.BlogSort;
import com.duck.code.commons.entity.pojo.BlogTag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    /**
     * desc: 查询每个标签下的博文总数
     * <p>
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BlogTagVO> queryTotalNumOfArticlesInTag(@Param("pageNum") Long pageNum, @Param("pageSize")  Long pageSize);

    /**
     * desc: 查询所有的标签信息
     * <p>
     *
     * @param
     * @return
     */
    @Select("select * from tb_blog_tag where deleted=0")
    List<BlogTag> queryAllTags();


    /**
     * desc: 获取排序值最大的一个标签信息
     * <p>
     *
     * @param
     * @return
     */
    @Select("SELECT * FROM tb_blog_tag AS t WHERE t.`deleted`=0 ORDER BY t.`tag_index` DESC LIMIT 0,1")
    BlogTag queryMaxIndexOfTag();

}
