package com.vicras.controllers;

import com.vicras.repository.UserCodeRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Log4j
public class MainController {


    @Autowired
    UserCodeRepository hashRepository;
    String ans = "";
    @RequestMapping("/")
    public String welcome() {
        log.info("main method of main controller");
        log.error("main method of main controller");
        log.warn("main method of main controller");
        return "Welcome to RestTemplate Example.";
    }

    @RequestMapping("/hello/{player}")
    public String message(@PathVariable String player) {

        hashRepository.save("code1", 424L);
        hashRepository.save("code2", 43L);
        hashRepository.saveTemporarily("code3", 44L, 2, TimeUnit.MINUTES);
        boolean code1 = hashRepository.isExistWithCode("code1");
        boolean code3 = hashRepository.isExistWithCode("code3");
        hashRepository.findWithCode("code1").ifPresent((e)->
                ans += "code1 value: " + e);
        ans += "code1 is exist: " + code1;
        ans += " code3 is exist: " + code3;
        return ans;
    }
}

