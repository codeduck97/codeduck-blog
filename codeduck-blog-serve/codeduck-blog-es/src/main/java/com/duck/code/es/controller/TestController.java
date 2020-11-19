package com.duck.code.es.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/es/test")
    public String test() {
        return "hello, this is es page.";
    }

}
