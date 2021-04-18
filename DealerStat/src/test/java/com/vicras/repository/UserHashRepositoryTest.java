package com.vicras.repository;

import com.vicras.config.DatabaseConfig;
import com.vicras.config.InitializerConfig;
import com.vicras.config.WebConfig;
import com.vicras.entity.UserHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;


@ContextConfiguration(classes = {InitializerConfig.class, DatabaseConfig.class, WebConfig.class})
class UserHashRepositoryTest {
    @Autowired
    UserHashRepository hashRepository;

    @Test
    void testHashRepos(){
        hashRepository.save(new UserHash("jafs", 12L));
    }

}