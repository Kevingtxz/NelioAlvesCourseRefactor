package com.kevin.courserefactor.base.service;

import com.kevin.courserefactor.base.domain.StoreOrder;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(StoreOrder obj);

    void sendEmail(SimpleMailMessage msg);
}
