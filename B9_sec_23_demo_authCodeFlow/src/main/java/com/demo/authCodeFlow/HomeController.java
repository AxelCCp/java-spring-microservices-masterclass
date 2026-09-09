package com.demo.authCodeFlow;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;


@RestController 
public class HomeController {

    /* 
    @GetMapping("/")
    public String home() {
        return "hola!!! ... xd";
    }
    */

    @GetMapping("/")
    public String home(OAuth2AuthenticationToken token) {

        String email = token.getPrincipal().getAttribute("email");
        String name = token.getPrincipal().getAttribute("name");
        String roles = token.getAuthorities().toString();
        return "hola!!! ... " + email + ", " + name + ", " + roles + "...";

    }
    
}
