package com.duck.code.admin.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 图片信息表
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_pic")
@ApiModel(value="Picture对象", description="图片信息表")
public class PictureVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "图片别名：不唯一")
    private String alias;

    @ApiModelProperty(value = "原图片名")
    private String originalName;

    @ApiModelProperty(value = "现图片名：唯一")
    private String picName;

    @ApiModelProperty(value = "服务器图片地址")
    private String picUrl;

    @ApiModelProperty(value = "七牛云图片存储地址")
    private String qiNiuUrl;

    @ApiModelProperty(value = "markdown图片地址")
    private String mdUrl;

    @ApiModelProperty(value = "文件后缀名")
    private String suffixName;

    @ApiModelProperty(value = "图片大小")
    private Integer picSize;

    @ApiModelProperty(value = "图片类别id")
    private String sortId;

    @ApiModelProperty(value = "删除状态")
    private Boolean deleted;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
