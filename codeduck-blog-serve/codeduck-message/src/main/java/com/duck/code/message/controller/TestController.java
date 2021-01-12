package com.duck.code.message.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-10 14:44
 */
@RestController
@RequestMapping("/api/message/")
public class TestController {

    @GetMapping("/index")
    public String testIndex(){
        return "hello";
    }
}
