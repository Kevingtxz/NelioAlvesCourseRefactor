package com.kevin.courserefactor.base.service;

import com.kevin.courserefactor.base.domain.StoreOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {


    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(StoreOrder obj) {
        SimpleMailMessage smm = this.prepareSimpleMailMessageFromStoreOrder(obj);
        this.sendEmail(smm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromStoreOrder(StoreOrder obj) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(obj.getClient().getEmail());
        smm.setFrom(this.sender);
        smm.setSubject("Order confirmed! Code: " + obj.getId());
        smm.setSentDate(new Date(System.currentTimeMillis()));
        smm.setText(obj.toString());
        return smm;
    }
}
