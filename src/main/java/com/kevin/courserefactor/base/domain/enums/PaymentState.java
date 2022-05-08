package com.kevin.courserefactor.base.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentState {

    PENDING(1, "Pending"),
    PAID(2, "Paid"),
    CANCELED(3, "Canceled");

    private int cod;
    private String description;

    public static PaymentState toEnum(Integer cod) {
        if (cod == null) return null;
        for (PaymentState paymentState : PaymentState.values())
            if (cod.equals(paymentState.getCod()))
                return paymentState;
        throw new IllegalStateException("Id inválido: " + cod);
    }
}
