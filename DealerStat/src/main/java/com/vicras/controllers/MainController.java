package com.vicras.controllers;

import com.vicras.entity.UserHash;
import com.vicras.repository.UserHashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class MainController {


    @Autowired
    UserHashRepository hashRepository;

    @RequestMapping("/")
    public String welcome() {//Welcome page, non-rest
        return "Welcome to RestTemplate Example.";
    }

    @RequestMapping("/hello/{player}")
    public String message(@PathVariable String player) {
        hashRepository.save(new UserHash("has12h", 1234L));
        String collect = hashRepository.findAll().entrySet().stream().map(e -> String.valueOf(e.getKey() + " ----- " + e.getValue())).collect(Collectors.joining("\n"));
        return collect;
    }
}

