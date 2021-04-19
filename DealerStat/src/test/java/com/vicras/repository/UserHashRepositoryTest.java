package com.vicras.repository;

import com.vicras.config.DatabaseConfig;
import com.vicras.config.InitializerConfig;
import com.vicras.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = {InitializerConfig.class, DatabaseConfig.class, WebConfig.class})
class UserHashRepositoryTest {
    @Autowired
    UserCodeRepository hashRepository;

    @Test
    void testHashRepos(){
//        hashRepository.save(new UserHash("jafs", 12L));
    }

}