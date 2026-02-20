package com.api.gateway;


import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(10, 20, 1);
    }  
    
    @Bean 
    public KeyResolver hostNameKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    //docker run -d --name redis -p 6379:6379 redis:latest

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product", r -> 
                r.path("/api/products/**") //r.path("/api/products/**")
                //.filters(f -> f.rewritePath("/products(?<segment>/?.*)", "/api/products${segment}"))  //filtro cambia ruta.
                
                .filters(f -> 
                        f.retry(retryconfig -> retryconfig.setRetries(10).setMethods(HttpMethod.GET)).//246 circuitBreaker y retry.
                        //con gateway es mejor redis
                        requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter()).setKeyResolver(hostNameKeyResolver())).//250 rate limiter con redis.
                        
                        /*f.*/circuitBreaker(config -> config.setName("ecomBreaker").setFallbackUri("forward:/fallback/products"))) //solo circuitBreaker.

                .uri("lb://product"))

                .route("user", r -> 
                r.path("/api/users/**") //r.path("/api/users/**")
                //.filters(f -> f.rewritePath("/users(?<segment>/?.*)", "/api/users${segment}"))
                .uri("lb://user"))
                
                
                .route("order", r -> 
                r.path("/api/order/**", "/api/cart/**") // r.path("/api/order/**", "api/cart/**")
                //.filters(f -> f.rewritePath("/(?<segment>.*)", "/api/${segment}"))
                .uri("lb://order"))

                .route("eureka", r -> 
                        r.path("/eureka/main")
                        .filters(f -> f.rewritePath("/eureka/main", "/"))
                        .uri("http://localhost:8761"))

                .route("eureka-static", r -> 
                        r.path("/eureka/**")
                        .uri("http://localhost:8761"))

                .build();
    }

}
