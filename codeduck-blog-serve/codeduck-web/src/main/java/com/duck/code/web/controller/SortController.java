package com.duck.code.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: codeduck-blog-serve
 * @description: 分类信息控制器
 * @author: Code Duck
 * @create: 2020-11-22 15:24
 */
@RestController
@RequestMapping("/api/sort")
@Validated
@Api(value = "分类页面相关接口")
@Slf4j
public class SortController {
}
