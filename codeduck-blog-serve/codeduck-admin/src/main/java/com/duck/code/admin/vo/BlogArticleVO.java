package com.duck.code.admin.vo;

import com.duck.code.commons.entity.pojo.BlogSort;
import com.duck.code.commons.entity.pojo.BlogTag;
import com.duck.code.commons.validator.Insert;
import com.duck.code.commons.validator.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @program: codeduck-blog-serve
 * @description: 博客显示层对象
 * @author: Code Duck
 * @create: 2020-10-24 09:49
 */
@Data
@ApiModel(value="BlogArticleVO对象", description="用于前端显示博文的对象")
public class BlogArticleVO {

    /**
     * 博文id
     */
    @ApiModelProperty(value = "博文id：添加新的博文时，不需要设置",required = true)
    @NotBlank(message = "博文id不能为空", groups = Update.class)
    private String id;

    /**
     * 管理员id
     */
    @ApiModelProperty(value = "管理员id")
    private String adminId;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者",required = true)
    @NotBlank(message = "作者不能为空",groups = {Update.class,Insert.class})
    private String author;

    /**
     * 博文标题
     */
    @ApiModelProperty(value = "博文标题",required = true)
    @NotBlank(message = "博文标题不能为空",groups = {Insert.class,Update.class})
    private String title;

    /**
     * 博文内容
     */
    @ApiModelProperty(value = "博文内容",required = true)
    @NotBlank(message = "博文内容不能为空",groups = {Insert.class,Update.class})
    private String content;


    /**
     * 博文分类id
     */
    @ApiModelProperty(value = "博文分类id")
//    @NotBlank(message = "博文分类id不能为空",groups = {Insert.class,Update.class})
    private BlogSort sort;

    /**
     * 博文标签id
     */
    @ApiModelProperty(value = "博文标签id")
//    @NotBlank(message = "博文标签id不能为空",groups = {Insert.class,Update.class})
//    private String tagId;
    private BlogTag tag;

    /**
     * 博文是否发布
     */
    @ApiModelProperty(value = "0表示发布，1表示不发布",required = true)
    @Max(value = 1,groups = {Insert.class, Update.class})
    @Min(value = 0,groups = {Insert.class, Update.class})
    private Integer published;

    /**
     * 博文封面
     */
    @ApiModelProperty(value = "博文封面")
    private String cover;

    /**
     * 点击数
     */
    @ApiModelProperty(value = "文章点击数")
    private Integer hits;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除0:正常；1删除")
    private Integer deleted;

}
