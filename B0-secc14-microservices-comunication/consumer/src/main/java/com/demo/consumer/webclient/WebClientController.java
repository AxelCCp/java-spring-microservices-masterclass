package com.demo.consumer.webclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/webclient")
public class WebClientController {

    private final ProviderWebClient providerWebClient;

    public WebClientController(ProviderWebClient providerWebClient) {
        this.providerWebClient = providerWebClient;
    }

    @GetMapping("/info")
    public Mono<String> getInstance() {
        //WebClient webClient = WebClient.create();
        //return webClient.get().uri("http://localhost:8081/info").retrieve().bodyToMono(String.class);
        return this.providerWebClient.getInstanceInfo();
    }
}
