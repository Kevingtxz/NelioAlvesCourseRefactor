package com.kevin.courserefactor.config.test.configs;

import com.kevin.courserefactor.base.service.email.EmailService;
import com.kevin.courserefactor.base.service.email.MockMailService;
import com.kevin.courserefactor.config.test.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() {
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new MockMailService();
    }


}