package com.duck.code.admin.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.admin.service.PermissionService;
import com.duck.code.commons.constant.ResCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: codeduck-blog-serve
 * @description: 权限控制器
 * @author: Code Duck
 * @create: 2020-11-29 14:48
 */
@RestController
@Slf4j
@RequestMapping("/api/role")
@Api(value = "角色权限 API", tags = {"角色权限 API"})
public class RoleController {

    @Resource
    private PermissionService permissionService;

    @RequiresPermissions("role:list")
    @ApiOperation(value = "获取角色列表")
    @GetMapping("/list")
    public R getRoleList(){
        List<JSONObject> roleList = permissionService.getRoleList();
        return R.ok(roleList).setCode(ResCode.OPERATION_SUCCESS);
    }

    @RequiresPermissions("role:list")
    @ApiOperation(value = "获取所有权限信息")
    @GetMapping("/permission")
    public R getAllPermission() {
        List<JSONObject> result = permissionService.getAllPermission();
        return R.ok(result).setCode(ResCode.OPERATION_SUCCESS);
    }
}
