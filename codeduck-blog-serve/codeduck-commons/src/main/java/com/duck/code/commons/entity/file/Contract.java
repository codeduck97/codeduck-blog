package com.duck.code.commons.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code Duck
 * @since 2021-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_contract")
@ApiModel(value="Contract对象", description="")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合同文件id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件内容")
    private String content;

    @ApiModelProperty(value = "文件级别")
    private String fileLevel;

    @ApiModelProperty(value = "文件所属地区")
    private String region;

    @ApiModelProperty(value = "文件上传时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "文件修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "文件是否被删除：0未被删除，1已删除")
    private Integer deleted;


}
