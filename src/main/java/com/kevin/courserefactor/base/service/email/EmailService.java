package com.kevin.courserefactor.base.service.email;

import com.kevin.courserefactor.base.domain.StoreOrder;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(StoreOrder obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(StoreOrder obj);

    void sendHtmlEmail(MimeMessage msg);
}
