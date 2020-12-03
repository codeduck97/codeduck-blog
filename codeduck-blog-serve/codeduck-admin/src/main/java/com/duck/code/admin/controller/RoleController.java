package com.duck.code.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.admin.service.AdminService;
import com.duck.code.admin.service.PermissionService;
import com.duck.code.admin.service.RoleService;
import com.duck.code.admin.service.UserRoleService;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.entity.sys.Role;
import com.duck.code.commons.utils.CommonRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: codeduck-blog-serve
 * @description: 权限控制器
 * @author: Code Duck
 * @create: 2020-11-29 14:48
 */
@RestController
@Slf4j
@Validated
@RequestMapping("/api/role")
@Api(value = "角色权限 API", tags = {"角色权限 API"})
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private UserRoleService userRoleService;

    private String message;

    @ApiOperation(value = "获取角色列表")
    @PostMapping("/list")
    public R getRolesInfo(@Min(value = 1, message = "当前页 pageNum >= 1") @RequestParam(value = "pageNum", defaultValue = "1") long pageNum,
                          @Min(value = 1, message = "页面大小 pageSize >= 1") @RequestParam(value = "pageSize", defaultValue = "5") long pageSize) {
        IPage<Role> roles = roleService.getRolesInfo(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("total", roles.getTotal());
        map.put("roles", roles.getRecords());
        return CommonRes.success(map);
    }

    @ApiOperation(value = "获取权限列表")
    @GetMapping("/permission")
    public R getPermissionMenus() {
        Map<String, Object> res = permissionService.getPermissionMenus();
        return CommonRes.success(res);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/add")
    public R addRoleInfo(@RequestBody @Valid Role role) {
        try {
            roleService.addRole(role);
            return CommonRes.success(null);
        } catch (Exception e) {
            message = "新增角色失败";
            log.error(message, e);
            return CommonRes.fail(message);
        }
    }

    @ApiOperation(value = "删除角色信息")
    @DeleteMapping("/{roleId}")
    public R deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleId) {
        // 查看该角色关联的用户
        List<String> userIds = userRoleService.getUserIdsByRoleId(roleId);
        if (!userIds.isEmpty()) {
            return R.failed("删除失败，某些用户拥有该角色").setCode(ResCode.OPERATION_REJECT);
        }
        if (this.roleService.deleteRoles(roleId)) {
            return CommonRes.success(null);
        } else {
            return CommonRes.fail("删除失败");
        }
    }
}
