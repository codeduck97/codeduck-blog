package com.duck.code.commons.entity.picture;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("tb_picture")
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 图片别名：用于对前端展示
     */
    private String alias;

    /**
     * 原图片名称
     */
    private String originalName;

    /**
     * 现图片名称
     */
    private String picName;

    /**
     * 本地图片存储地址
     */
    private String localUrl;

    /**
     * 七牛云存储地址
     */
    private String qiNiuUrl;

    /**
     * 图片markdown地址
     */
    private String mdUrl;

    /**
     * 后缀名
     */
    private String suffixName;

    /**
     * 图片大小
     */
    private Integer picSize;

    /**
     * 图片分辨率
     */
    private String resolution;

    /**
     * 是否删除：0正常，1删除
     */
    @TableLogic
    private Integer deleted;

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


}
