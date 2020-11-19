package com.duck.code.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CodeduckBlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeduckBlogServerApplication.class, args);
    }

}
