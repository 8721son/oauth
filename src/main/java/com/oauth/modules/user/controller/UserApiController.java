package com.oauth.modules.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/user")
public class UserApiController {

    @GetMapping("/test")
    public String test(){
        System.out.println("test");
        return "test";
    }
}
