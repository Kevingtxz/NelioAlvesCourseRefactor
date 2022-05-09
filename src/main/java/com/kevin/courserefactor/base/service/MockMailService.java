package com.kevin.courserefactor.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockMailService extends AbstractEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockMailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOGGER.info("Simulating sending email...");
        LOGGER.info(msg.toString());
        LOGGER.info("Email enviado");
    }
}
