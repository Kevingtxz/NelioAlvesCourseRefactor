package com.kevin.courserefactor.base.service.ticket;

import com.kevin.courserefactor.base.domain.PaymentWithTicketEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TicketService {

    public void completePaymentWithTicket(PaymentWithTicketEntity payment, Date instant) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instant);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        payment.setDateLimit(calendar.getTime());

    }
}
