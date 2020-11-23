package com.duck.code.admin.entity;

import com.duck.code.commons.validator.Insert;
import com.duck.code.commons.validator.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2020-10-27 17:38
 */
@Data
@ApiModel(value="BlogSort对象", description="博客分类表")
public class BlogSortVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "博文分类id：添加博文分类时不需要设置",required = true)
    @NotBlank(message = "博文分类id不能为空",groups = Update.class)
    private String id;

    @ApiModelProperty(value = "分类名称",required = true)
    @NotBlank(message = "博文分类名称不能为空",groups = Update.class)
    private String sortName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "排序字段，越大越靠前",required = true)
    @Min(groups = {Update.class, Insert.class} ,value = 0, message = "最小值为0")
    private Integer sortIndex;

    @ApiModelProperty(value = "点击数")
    private Integer hits;

    @ApiModelProperty(value = "该类别下包含的文章数")
    private Integer articlesNum;

    @ApiModelProperty(value = "是否被删除：0正常，1删除")
    private Integer deleted;

    @ApiModelProperty(value = "分类状态：0正常")
    private Integer status;
}
