package com.kevin.courserefactor.base.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.kevin.courserefactor.base.domain.enums.PaymentState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonTypeName("paymentWithCard")
public class PaymentWithCard extends Payment {
    private static final long serialVersionUID = 1L;

    private Integer installments;

    public PaymentWithCard(Integer id, PaymentState paymentState, StoreOrder storeOrder, Integer installments) {
        super(id, paymentState, storeOrder);
        this.installments = installments;
    }
}
