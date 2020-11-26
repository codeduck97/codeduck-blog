package com.duck.code.web.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.commons.entity.pojo.BlogTag;
import com.duck.code.web.vo.BlogTagVO;
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
     * desc: 查询标签集合（每个标签中存在博文）
     * <p>
     *
     * @param
     * @return
     */
    @Select("SELECT * FROM tb_blog_tag AS t LEFT JOIN tb_blog AS b ON t.`id` = b.`tag_id` WHERE t.`deleted`=0 AND b.`deleted`=0 GROUP BY t.`id`")
    List<BlogTag> queryAllTagsByBlogId();

}
