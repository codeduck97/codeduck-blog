package com.duck.code.web.controller;

import com.duck.code.web.feign.ServerFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    ServerFeignService serverFeignService;

    @GetMapping("/test")
    public String test(){
        return "hello,this is web page.";
    }

    @GetMapping("/fromServer")
    public String testFromServer(){
        return serverFeignService.getPageFromServer();
    }
}
