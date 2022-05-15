package com.kevin.courserefactor.base.service.email;

import com.kevin.courserefactor.base.domain.OrderEntity;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(OrderEntity obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(OrderEntity obj);

    void sendHtmlEmail(MimeMessage msg);
}
