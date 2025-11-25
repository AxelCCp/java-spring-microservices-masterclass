package com.info.configdemo.controller;

import com.info.configdemo.BuildInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class BuildInfoController {
    @Value("${build.id:default}")
    private String buildId;
    @Value("${build.version:default}")
    private String buildVersion;
    @Value("${build.name:default}")
    private String buildName;
    private BuildInfo buildInfo;
    public BuildInfoController(BuildInfo buildInfo) {
        this.buildInfo = buildInfo;
    }
    @GetMapping("/build-info")
    public String getBuildInfo() {
        return  "Build id: " + this.buildId + ", version: " + this.buildVersion + ", name: " + this.buildName + ".";
    }
    @GetMapping("/build2-info")
    public String getBuildInfo2() {
        return  "Build 2 --> id: " + this.buildInfo.getId() + ", version: " + this.buildInfo.getVersion() + ", name: " + this.buildInfo.getName() + ".";
    }
}
