package com.duck.code.admin.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.admin.service.PictureService;
import com.duck.code.admin.service.QiNiuService;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.entity.picture.Picture;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private PictureService pictureService;

    @Resource
    private QiNiuService qiNiuService;

    @ApiOperation(value = "服务器图片上传接口", notes = "此接口将图片上传至本地服务器")
    @ApiImplicitParam(name = "pictures", value = "图片文件集合", required = true)
    @PostMapping("/upload/serve")
    public R uploadPicsToServe(@RequestParam("file") MultipartFile picture) {
        Picture pic = pictureService.batchUploadPicToServe(picture);
        if (pic == null) return R.failed("图片上传失败！").setCode(ResCode.OPERATION_FAIL);
        log.info("图片信息已保存至数据库{{}}", pic);
        return R.ok(pic).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "七牛云图片上传接口", notes = "此接口将图片上传至七牛云存储")
    @ApiImplicitParam(name = "pictures", value = "图片文件集合", required = true)
    @PostMapping("/upload/qiniu")
    public R uploadPicsToQiNiu(@RequestParam("file") MultipartFile picture) {
        Picture pic = qiNiuService.uploadFile(picture);
        if (pic == null) return R.failed("图片上传失败！").setCode(ResCode.OPERATION_FAIL);
        log.info("图片信息已保存至数据库{{}}", pic);
        return R.ok(pic).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "获取图片列表", notes = "分页获取图片列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码，默认值：1，最小值：1", paramType = "query", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认值：5，最小值：1", paramType = "query", defaultValue = "5", required = true)
    })
    @PostMapping("/list")
    public R getBlogTagList(@Min(value = 1, message = "当前页 pageNum >= 1") @RequestParam(value = "pageNum", defaultValue = "1") long pageNum,
                            @Min(value = 1, message = "页面大小 pageSize >= 1") @RequestParam(value = "pageSize", defaultValue = "5") long pageSize) {
        List<Picture> pictures = pictureService.getPictureList(pageNum, pageSize);
        int total = pictureService.count();
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("pictures", pictures);
        resMap.put("total", total);
        log.info("图片列表被请求，pageNum：{{}}，pageSize：{{}}", pageNum, pageSize);
        return R.ok(resMap).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "批量删除图片信息")
    @PostMapping("/delete")
    public R deletePicture(@RequestBody String[] pictureIds) {
        List<String> ids = new ArrayList<>();
        for (String id : pictureIds) {
            if (!id.isEmpty()) {
                ids.add(id);
                log.info("图片信息即将被删除{{}}", pictureService.getById(id));
            }
        }
        if (pictureService.removeByIds(ids)) {
            log.info("！！！！！已全部删除！！！！！");
            return R.ok("删除成功").setCode(ResCode.OPERATION_SUCCESS);
        }
        log.info("！！！！！删除失败！！！！！");
        return R.failed("删除失败").setCode(ResCode.OPERATION_FAIL);
    }
}
