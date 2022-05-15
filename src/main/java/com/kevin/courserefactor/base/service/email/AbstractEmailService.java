package com.kevin.courserefactor.base.service.email;

import com.kevin.courserefactor.base.domain.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(OrderEntity obj) {
        SimpleMailMessage smm = this.prepareSimpleMailMessageFromOrder(obj);
        this.sendEmail(smm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromOrder(OrderEntity obj) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(obj.getClient().getEmail());
        smm.setFrom(this.sender);
        smm.setSubject("Order confirmed! Code: " + obj.getId());
        smm.setSentDate(new Date(System.currentTimeMillis()));
        smm.setText(obj.toString());
        return smm;
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(OrderEntity obj) {
        try {
            MimeMessage mm = this.prepareMimeMessageFromOrder(obj);
            this.sendHtmlEmail(mm);
        } catch (MessagingException e) {
            this.sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMessageFromOrder(OrderEntity obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(obj.getClient().getEmail());
        mmh.setFrom(this.sender);
        mmh.setSubject("Order confirmed! Code: " + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(this.htmlFromTemplateOrder(obj), true);
        return mimeMessage;
    }

    protected String htmlFromTemplateOrder(OrderEntity obj) {
        Context context = new Context();
        context.setVariable("order", obj);
        return templateEngine.process("email/confirmationOrder", context);
    }
}
