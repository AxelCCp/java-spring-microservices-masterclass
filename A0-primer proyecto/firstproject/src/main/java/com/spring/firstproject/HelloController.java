package com.spring.firstproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Struct;

@RestController
public class HelloController {

    //http://localhost:8080/hello
    @GetMapping("/hello")
    public String hello() {
        return "hello ...";
    }

    @PostMapping("/hello")
    public String helloPost(@RequestBody String name) {
        return "hello " + name;
    }


}
