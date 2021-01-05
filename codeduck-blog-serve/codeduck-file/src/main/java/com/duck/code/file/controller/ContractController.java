package com.duck.code.file.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.entity.file.Contract;
import com.duck.code.file.service.ContractService;
import com.duck.code.file.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Code Duck
 * @since 2021-01-05
 */
@Api(tags = "合同文件控制器")
@RestController
@RequestMapping("/api/contract")
public class ContractController {

    @Resource
    private ContractService contractService;

    @ApiOperation(value = "文件上传接口")
    @ApiImplicitParam(name = "file", value = "上传的文件类型为MultipartFile", required = true, paramType = "query")
    @PostMapping("/upload")
    public boolean uploadContract(@RequestBody Contract contract) {
        return contractService.save(contract);
    }

}
