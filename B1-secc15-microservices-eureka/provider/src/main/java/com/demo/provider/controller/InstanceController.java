package com.demo.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstanceController {

    @Value("${server.port}")
    private String port;

    private final String instanceId = java.util.UUID.randomUUID().toString();


    @GetMapping("/info")
    public String getInstanceInfo() {
        System.out.println("request received - port " + port);
        return  "Instance server by port: " + port + ".Instance ID: " + instanceId;
    }


}
