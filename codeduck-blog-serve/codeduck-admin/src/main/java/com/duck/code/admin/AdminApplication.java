package com.duck.code.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients("com.duck.code.commons.feign")
@ComponentScan(basePackages = {
        "com.duck.code.commons.config",
        "com.duck.code.commons.exception",
        "com.duck.code.admin"
})
@MapperScan("com.duck.code.admin.config")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
