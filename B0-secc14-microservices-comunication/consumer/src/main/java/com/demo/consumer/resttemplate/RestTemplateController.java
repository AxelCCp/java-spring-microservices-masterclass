package com.demo.consumer.resttemplate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/rest-template")
public class RestTemplateController {

    private final RestTemplateClient restTemplateClient;

    public RestTemplateController(RestTemplateClient restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
    }

    @GetMapping("/instance")
    public String getInstance() {
        //RestTemplate restTemplate = new RestTemplate();
        //String resp = restTemplate.getForObject(, String.class);
        //return "Hi..... " + resp;
        return this.restTemplateClient.getInstanceInfo();
    }

}
