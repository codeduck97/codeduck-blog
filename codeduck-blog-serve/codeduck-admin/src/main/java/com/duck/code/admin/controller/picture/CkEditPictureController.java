package com.duck.code.admin.controller.picture;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: codeduck-blog-serve
 * @description: CKEdit编辑器图片处理控制类
 * @author: Code Duck
 * @create: 2020-11-09 15:30
 */
@RestController
@RequestMapping("/api/ck")
@Slf4j
@Api(value = "CKeditor API", tags = {"CKeditor API"})
public class CkEditPictureController {


    /**
     * 通过URL将图片上传到自己服务器中【用于Github和Gitee的头像上传】
     *
     * @return
     */
    @ApiOperation(value = "通过URL上传图片", notes = "通过URL上传图片")
    @PostMapping("/upload/url")
    public R uploadPicsByUrl() {
        return R.ok("通过URL上传图片").setCode(10000);
    }


    /**
     * Ckeditor图像中的图片上传
     *
     * @return
     */
    @ApiOperation(value = "图像中的图片上传", notes = "图像中的图片上传")
    @PostMapping(value = "/upload/pic")
    public R ckeditorUploadFile(@RequestParam("upload") MultipartFile picture) {
        Map<String,Object> map = new HashMap<>();
        map.put("uploaded", 1);
        map.put("fileName", "foo.jpg");
        map.put("url", "/files/foo.jpg");
        String s = JSONUtil.toJsonStr(map);
        log.info("{{}}",picture.getOriginalFilename());
        return R.ok(map).setCode(10000);
    }

    /**
     * Ckeditor复制的图片上传
     *
     * @return
     */
    @ApiOperation(value = "复制的图片上传", notes = "复制的图片上传")
    @RequestMapping(value = "/upload/copy", method = RequestMethod.POST)
    public synchronized Object ckeditorUploadCopyFile() {
        return R.ok("Ckeditor复制的图片上传").setCode(10000);
    }

    /**
     * Ckeditor工具栏 “插入\编辑超链接”的文件上传
     *
     * @return
     */
    @ApiOperation(value = "工具栏的文件上传", notes = "工具栏的文件上传")
    @RequestMapping(value = "/upload/tool", method = RequestMethod.POST)
    public Object ckeditorUploadToolFile(HttpServletRequest request) {
        return R.ok("工具栏的文件上传").setCode(10000);
    }
}
