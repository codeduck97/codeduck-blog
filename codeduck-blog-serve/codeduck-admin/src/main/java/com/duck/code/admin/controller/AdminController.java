package com.duck.code.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.admin.service.AdminService;
import com.duck.code.admin.utils.AdminUtil;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.entity.sys.Admin;
import com.duck.code.commons.validator.Insert;
import com.duck.code.commons.validator.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @description: 用户信息控制器
 * @author: Code Duck
 * @create: 2020-10-03 18:18
 */
@RestController
@Slf4j
@Validated
@RequestMapping("/api/user")
@Api(value = "用户信息管理API", tags = {"用户信息管理 API"})
public class AdminController {

    @Resource
    private AdminService adminService;

    @ApiOperation(value = "更新用户信息",notes = "用户更新功能待修改……目前可以对用户名和密码同时修改，但用户名不能同名")
    @PutMapping("/update")
    public R updateUserInfo(@Validated({Update.class}) @RequestBody Admin adminVO) {
        log.info("{{}}",adminVO);
        // 用户修改信息时，用户名必须唯一
        Admin admin = adminService.getAdminByName(adminVO.getUsername());
        if (admin == null || admin != null && admin.getId().equals(adminVO.getId())) {
            admin.setPassword(AdminUtil.md5UserPwd(adminVO.getPassword()));
            admin.setUpdateTime(LocalDateTime.now());
            admin.setRoleId(adminVO.getRoleId());
            try {
                if (adminService.updateUser(admin)){
                    log.info("用户信息已被修改 {{}}", admin);
                    return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
                }
            } catch (Exception e) {
                log.info("用户更新发生异常{{}}",e.getMessage());
                return R.failed("用户信息修改失败").setCode(ResCode.OPERATION_FAIL);
            }
        }
        return R.failed("该用户已存在（与已存在的用户名冲突）").setCode(ResCode.OPERATION_REJECT);
    }


    @ApiOperation(value = "删除用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", paramType = "query", required = true)
    @DeleteMapping("/delete")
    public R deleteUserInfo(@NotBlank(message = "用户id不能为空") @RequestParam("id") String id) {
        Admin admin = adminService.getById(id);
        if (adminService.deleteUserById(id)) {
            log.info("用户信息已被删除 {{}}",admin);
            return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
        }
        return R.failed("该用户不存在，删除失败").setCode(ResCode.OPERATION_FAIL);
    }

    @PostMapping("/add")
    @ApiOperation(value = "注册或更新用户信息", notes = "请注意必填属性信息")
    public R registerUserInfo(@Validated({Insert.class}) @RequestBody Admin admin) {
        log.info("{{}}",admin);
        // 1.判断当前注册的用户名是否合法
        if (!adminService.existAdminByName(admin.getUsername())) {
            admin.setPassword(AdminUtil.md5UserPwd(admin.getPassword()));
            try {
                if (adminService.addUser(admin)) {
                    log.info("更新用户信息 {{}}", admin);
                    return R.ok(null).setCode(ResCode.OPERATION_SUCCESS).setMsg("用户注册成功");
                }
            } catch (Exception e) {
                log.error("添加用户操作发生异常{{}}",e.getCause());
                return R.failed("用户注册失败").setCode(ResCode.OPERATION_FAIL);
            }
        }
        return R.failed("该用户已存在").setCode(ResCode.OPERATION_REJECT);
    }

    @ApiOperation(value = "获取管理员信息列表", notes = "分页获取管理员标签列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码，默认值：1，最小值：1", paramType = "query", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认值：5，最小值：1", paramType = "query", defaultValue = "5", required = true)
    })
    @PostMapping("/list")
    public R getAllUserInfo(@Min(value = 1, message = "当前页 pageNum >= 1") @RequestParam(value = "pageNum", defaultValue = "1") long pageNum,
                            @Min(value = 1, message = "页面大小 pageSize >= 1") @RequestParam(value = "pageSize", defaultValue = "5") long pageSize) {
        IPage<Admin> adminList = this.adminService.getAdminList(pageNum, pageSize);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", adminList.getTotal());
        map.put("admins", adminList.getRecords());
        return R.ok(map).setCode(ResCode.OPERATION_SUCCESS);
    }

    /**
     * desc: 根据用户名查询用户信息
     * <p>
     *
     * @param username
     * @return
     */
    @GetMapping("/get")
    @ApiOperation(value = "根据用户名查询用户信息")
    public R getUserInfoByName(@NotBlank(message = "用户名不能为空") @RequestParam("username") String username) {
        Admin admin = adminService.getAdminByName(username);
        if (admin != null) {
            log.info("请求查询用户信息 {{}}", admin);
            return R.ok(admin).setCode(ResCode.OPERATION_SUCCESS);
        }
        return R.failed("未查询到该用户信息").setCode(ResCode.OPERATION_FAIL);
    }
}
