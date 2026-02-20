package com.demo.consumer.httpinterface;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return  WebClient.builder();
    }

    @Bean
    public ProviderHttpInterface webClientHttpInterface(WebClient.Builder webClientBuilder) {
        //WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081").build();
        WebClient webClient = webClientBuilder.baseUrl("http://provider").build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        ProviderHttpInterface service = factory.createClient(ProviderHttpInterface.class);
        return service;
    }

}
