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
public class PaymentWithCardEntity extends PaymentEntity {
    private static final long serialVersionUID = 1L;

    private Integer installments;

    public PaymentWithCardEntity(Integer id, PaymentState paymentState, OrderEntity storeOrder, Integer installments) {
        super(id, paymentState, storeOrder);
        this.installments = installments;
    }
}
