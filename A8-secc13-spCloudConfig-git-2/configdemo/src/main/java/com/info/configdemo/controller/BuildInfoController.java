package com.info.configdemo.controller;

import com.info.configdemo.BuildInfo;
import com.info.configdemo.DeploymentProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildInfoController {

    private final DeploymentProperties deploymentProperties;

    public BuildInfoController(DeploymentProperties deploymentProperties) {
        this.deploymentProperties = deploymentProperties;
    }

    @GetMapping("/build-info")
    public String getBuildInfo() {
        return  "Despliegue: " + deploymentProperties.getName() + ", Versión: " + deploymentProperties.getVersion();
    }

}
