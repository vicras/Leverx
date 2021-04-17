package com.vicras.controllers;

import com.vicras.entity.Role;
import com.vicras.entity.User;
import com.vicras.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String welcome() {//Welcome page, non-rest
        return "Welcome to RestTemplate Example.";
    }

    @RequestMapping("/hello/{player}")
    public String message(@PathVariable String player) {
        User build = User.builder()
                .email("vasua@gmail.com")
                .firstName("vasua")
                .lastName("pupkin")
                .password("pupkin12345")
                .role(Role.ADMIN)
                .build();
        userService.addNewUser(build);
        return "hello";
    }
}

