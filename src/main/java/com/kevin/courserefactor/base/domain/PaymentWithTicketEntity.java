package com.kevin.courserefactor.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.kevin.courserefactor.base.domain.enums.PaymentState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonTypeName("paymentWithTicket")
public class PaymentWithTicketEntity extends PaymentEntity {
    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateLimit;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date datePayment;


    public PaymentWithTicketEntity(Integer id, PaymentState paymentState, OrderEntity storeOrder, Date dateLimit, Date datePayment) {
        super(id, paymentState, storeOrder);
        this.dateLimit = dateLimit;
        this.datePayment = datePayment;
    }
}
