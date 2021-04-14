package com.codelearning.springrabbitmq;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class api {
    @RequestMapping("/app2")
    public String hello() {
        return "app2 hello";
    }
}

