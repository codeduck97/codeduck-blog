package com.duck.code.commons.entity.pojo;

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
 * 博客分类表
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_blog_sort")
public class BlogSort implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类 id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 分类名称
     */
    private String sortName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 排序字段，越大越靠前
     */
    private Integer sortIndex;

    /**
     * 分类状态
     */
    private Integer status;

    /**
     * 点击数
     */
    private Integer hits;

    /**
     * 是否被删除：0正常，1删除
     */
    @TableLogic
    private Integer deleted;
}
