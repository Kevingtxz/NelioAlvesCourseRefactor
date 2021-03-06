package com.kevin.courserefactor.config.db_simulation.config;

import com.kevin.courserefactor.base.service.email.EmailService;
import com.kevin.courserefactor.base.service.email.SmtpEmailService;
import com.kevin.courserefactor.config.db_simulation.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() {
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}
