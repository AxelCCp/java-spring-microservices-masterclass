package com.demo.consumer.httpinterface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/http-interface")
public class HttpInterfaceController {

    private final ProviderHttpInterface providerHttpInterface;

    public HttpInterfaceController(ProviderHttpInterface providerHttpInterface) {
        this.providerHttpInterface =  providerHttpInterface;
    }

    @GetMapping("/info")
    public String getInstance() {
        return this.providerHttpInterface.getInstance();
    }
}
