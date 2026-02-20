package com.demo.consumer.resttemplate;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("/instance")
public class RestTemplateClient {
    private final RestTemplate restTemplate;
    private static final String PROVIDER_URL = "http://provider";

    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getInstanceInfo() {
        return this.restTemplate.getForObject(PROVIDER_URL + "/info", String.class);
    }
}
