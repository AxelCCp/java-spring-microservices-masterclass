package com.demo.consumer.restClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/restclient")
public class RestClientController {

    private final ProviderRestClient providerRestClient;

    public RestClientController(ProviderRestClient providerRestClient) {
        this.providerRestClient = providerRestClient;
    }


    @GetMapping("/info")
    public String getInstance() {
        //RestClient restClient = RestClient.create();
        //return restClient.get().uri("http://localhost:8081/info").retrieve().body(String.class);
        return this.providerRestClient.getInstanceInfo();
    }
}
