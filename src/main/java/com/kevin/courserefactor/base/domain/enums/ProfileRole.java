package com.kevin.courserefactor.base.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfileRole {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(2, "ROLE_CLIENT");

    private int cod;
    private String description;

    public static ProfileRole toEnum(Integer cod) {
        if (cod == null) return null;
        for (ProfileRole paymentState : ProfileRole.values())
            if (cod.equals(paymentState.getCod()))
                return paymentState;
        throw new IllegalStateException("Id inválido: " + cod);
    }
}
