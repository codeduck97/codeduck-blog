package com.duck.code.commons.entity.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * <p>
 *
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_blog")
public class BlogArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博文id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 管理员id
     */
    private String adminId;

    /**
     * 作者
     */
    private String author;

    /**
     * 博文标题
     */
    private String title;

    /**
     * 博文内容
     */
    private String content;

    /**
     * 博文封面
     */
    private String cover;

    /**
     * 点击数
     */
    private Integer hits;

    /**
     * 博文更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 博文创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;

    /**
     * 博文分类id
     */
    private String sortId;

    /**
     * 博文标签id
     */
    private String tagId;

    /**
     * 是否发布：0发布，1不发布
     */
    private Integer published;

    /**
     * 删除状态：0未删除，1删除
     */
    @TableLogic
    private Integer deleted;
}
