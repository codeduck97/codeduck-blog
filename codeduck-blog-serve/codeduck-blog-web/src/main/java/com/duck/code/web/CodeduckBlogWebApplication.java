package com.duck.code.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.duck.code.web.feign")
public class CodeduckBlogWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeduckBlogWebApplication.class, args);
    }

}
