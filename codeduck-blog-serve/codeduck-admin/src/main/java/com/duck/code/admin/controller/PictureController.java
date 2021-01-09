package com.duck.code.admin.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.entity.picture.Picture;
import com.duck.code.commons.feign.PictureFeignClient;
import com.duck.code.commons.constant.CommonRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.Map;

/**
 * <p>
 * 图片信息表 前端控制器
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-04
 */
@RestController
@RequestMapping("/api/pic")
@Slf4j
@Api(value = "图片管理API", tags = {"图片管理 API"})
public class PictureController {

    @Resource
    private PictureFeignClient pictureFeignClient;

    @ApiOperation(value = "服务器图片上传接口", notes = "此接口将图片上传至本地服务器")
    @ApiImplicitParam(name = "pictures", value = "图片文件集合", required = true)
    @PostMapping("/upload/serve")
    public R uploadPicsToServe(@RequestParam("file") MultipartFile picture) {
        Picture pic = pictureFeignClient.uploadPicsToServe(picture);
        if (pic == null) return R.failed("图片上传失败！").setCode(ResCode.OPERATION_FAIL);
        return R.ok(pic).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "七牛云图片上传接口", notes = "此接口将图片上传至七牛云存储")
    @ApiImplicitParam(name = "pictures", value = "图片文件集合", required = true)
    @PostMapping("/upload/qiniu")
    public R uploadPicsToQiNiu(@RequestParam("file") MultipartFile picture) {
        Picture pic = pictureFeignClient.uploadPicsToQinNiu(picture);
        if (pic == null) return R.failed("图片上传失败！").setCode(ResCode.OPERATION_FAIL);
        return R.ok(pic).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "获取图片列表", notes = "分页获取图片列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码，默认值：1，最小值：1", paramType = "query", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认值：5，最小值：1", paramType = "query", defaultValue = "5", required = true)
    })
    @PostMapping("/list")
    public R getPictureList(@Min(value = 1, message = "当前页 pageNum >= 1") @RequestParam(value = "pageNum", defaultValue = "1") long pageNum,
                            @Min(value = 1, message = "页面大小 pageSize >= 1") @RequestParam(value = "pageSize", defaultValue = "5") long pageSize) {
        log.info("图片列表被请求，pageNum：{{}}，pageSize：{{}}", pageNum, pageSize);
        Map<String, Object> pictureList = pictureFeignClient.getPictureList(pageNum, pageSize);
        return CommonRes.success(pictureList);
    }

    @ApiOperation(value = "批量删除图片信息")
    @PostMapping("/delete")
    public R deletePicture(@RequestBody String[] pictureIds) {
        if (pictureFeignClient.deletePicture(pictureIds)) {
            return R.ok("删除成功").setCode(ResCode.OPERATION_SUCCESS);
        }
        return R.failed("删除失败").setCode(ResCode.OPERATION_FAIL);
    }
}
