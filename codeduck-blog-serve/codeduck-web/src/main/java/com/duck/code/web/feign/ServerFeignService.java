package com.duck.code.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient("codeduck-admin")
public interface ServerFeignService {

    @GetMapping("/test")
    String getPageFromServer();
}
