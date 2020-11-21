package com.duck.code.commons.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_pic_sort")
public class PicSort implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 分类名
     */
    private String sortName;

    /**
     * 是否删除:0正常，1删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime creationTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
