package com.duck.code.web.vo;


import com.duck.code.commons.validator.Insert;
import com.duck.code.commons.validator.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-30
 */
@Data
@ApiModel(value="BlogTag对象", description="博客标签表")
public class BlogTagVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "博文标签id，添加新的标签时不需要设置")
    @NotBlank(message = "id不能为空", groups = Update.class)
    private String id;

    @ApiModelProperty(value = "标签名",required = true)
    @NotBlank(groups = {Insert.class, Update.class},message = "标签名不能为空")
    private String tagName;

    @ApiModelProperty(value = "是否删除")
    private Integer deleted;

    @ApiModelProperty(value = "标签点击数")
    private Integer hits;

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
    private Integer tagIndex;

    @ApiModelProperty(value = "该标签下的博文数量")
    private Integer articlesNum;

    @ApiModelProperty(value = "标签状态：0正常")
    private Integer status;

}
