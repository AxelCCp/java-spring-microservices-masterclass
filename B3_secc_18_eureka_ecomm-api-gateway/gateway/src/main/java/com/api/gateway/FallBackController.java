package com.api.gateway;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class FallBackController {
   
  
    @GetMapping("/fallback/products")
    public ResponseEntity<List<String>>productsFallBack() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Collections.singletonList("product service unavailable ***"));
    }

}
