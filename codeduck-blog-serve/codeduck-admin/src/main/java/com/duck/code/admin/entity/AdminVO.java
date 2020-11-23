package com.duck.code.admin.entity;

import com.duck.code.commons.validator.Insert;
import com.duck.code.commons.validator.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @program: codeduck-auth
 * @description: VO 对应于Web页面上需要显示的数据
 * @author: Code Duck
 * @create: 2020-10-03 20:45
 */
@Data
@ApiModel(value = "Admin对象", description = "管理员实体对象")
public class AdminVO {

    @ApiModelProperty(value = "唯一id")
    @NotBlank(message = "id不能为空", groups = {Update.class})
    private String id;

    @ApiModelProperty(value = "管理员名", required = true)
    @NotBlank(message = "管理员名称不能为空", groups = {Insert.class})
    private String username;

    @ApiModelProperty(value = "管理员密码", required = true)
    @NotBlank(message = "密码不能为空", groups = {Insert.class, Update.class})
    private String password;

    @ApiModelProperty(value = "管理员登录次数")
    private Integer loginTimes;

    @ApiModelProperty(value = "0:正常 1：禁止登陆")
    private Integer status;

    @ApiModelProperty(value = "管理员头像")
    private String avatar;

    @ApiModelProperty(value = "管理员邮箱", required = true)
    @NotBlank(message = "邮箱不能为空", groups = {Insert.class, Update.class})
    private String email;

    @ApiModelProperty(value = "管理员手机号", required = true)
    @NotBlank(message = "手机号码不能为空", groups = {Insert.class, Update.class})
    private String mobile;

    @ApiModelProperty(value = "管理员qq号")
    private String qqNumber;

    @ApiModelProperty(value = "管理员创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;

    @ApiModelProperty(value = "管理员创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "最近登陆时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "管理员出生日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ApiModelProperty(value = "是否删除0:正常；1删除")
    private Integer deleted;
}
