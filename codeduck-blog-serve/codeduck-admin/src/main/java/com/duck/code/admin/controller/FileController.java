package com.duck.code.admin.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.admin.utils.FileUtil;
import com.duck.code.commons.constant.ResCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-05 12:55
 */
@Api(tags = "文件控制器")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @ApiOperation(value = "文件上传接口")
    @ApiImplicitParam(name = "file", value = "上传的文件类型为MultipartFile", required = true, paramType = "query")
    @PostMapping("/upload/contract")
    public R uploadContract(@RequestBody MultipartFile file, HttpServletRequest request) {
        return R.failed("error");
    }
}
