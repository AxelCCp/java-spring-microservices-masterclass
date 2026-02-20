package com.demo.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="provider", url="http://localhost:8081")
public interface ProviderFeignClient {

    @GetMapping("/info")
    String getInstanceInfo();
}
