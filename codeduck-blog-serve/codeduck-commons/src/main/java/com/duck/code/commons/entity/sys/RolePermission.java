package com.duck.code.commons.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色-权限关联表
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_permission")
@ApiModel(value="RolePermission对象", description="角色-权限关联表")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "权限id")
    private String permissionId;

    private LocalDateTime creationTime;

    private LocalDateTime updateTime;

    @ApiModelProperty(value = "0：未删除，1已删除")
    private Integer deleted;


}
