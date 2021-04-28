package com.vicras.controllers;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j
public class MainController {

    @GetMapping("/")
    public String welcome() {
        log.info("main method of main controller");
        log.error("main method of main controller");
        log.warn("main method of main controller");
        return "Welcome to RestTemplate Example.";
    }

    @RequestMapping("/hello/{user}")
    public String message(@PathVariable String user) {
        return "hello " + user;
    }
}

