package com.duck.code.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.commons.entity.pojo.BlogArticle;
import org.apache.ibatis.annotations.Param;
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

    /**
     * desc: 查询博文的创建时间以年月为单位进行分组汇总
     * <p>
     *
     * @param
     * @return
     */
    @Select("SELECT DATE_FORMAT(creation_time,'%Y-%m') AS time_line FROM tb_blog GROUP BY time_line ORDER BY time_line DESC")
    List<String> queryCreationTime();

    @Select("SELECT b.`id`,b.`title`,b.`tag_id`,b.`author`,b.`creation_time`,b.`cover` FROM tb_blog AS b WHERE DATE_FORMAT(b.`creation_time`,'%Y-%m') = #{date} AND b.`deleted`=0 ORDER BY b.`creation_time` DESC")
    List<BlogArticle> queryArticleByYearMonth(@Param("date") String date);
}
