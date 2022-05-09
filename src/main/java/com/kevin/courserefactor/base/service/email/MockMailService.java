package com.kevin.courserefactor.base.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public class MockMailService extends AbstractEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockMailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOGGER.info("Simulating sending email...");
        LOGGER.info(msg.toString());
        LOGGER.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOGGER.info("Simulating sending email HTML...");
        LOGGER.info(msg.toString());
        LOGGER.info("Email enviado");
    }
}
