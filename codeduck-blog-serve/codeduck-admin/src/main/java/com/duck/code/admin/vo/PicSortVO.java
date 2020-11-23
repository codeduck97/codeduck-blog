package com.duck.code.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 图片分类表
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PicSort对象", description="图片分类表")
public class PicSortVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "分类名")
    private String sortName;

    @ApiModelProperty(value = "是否删除")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime creationTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
