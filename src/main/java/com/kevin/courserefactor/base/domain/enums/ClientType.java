package com.kevin.courserefactor.base.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {

    NATURALPERSON(1, "Natural person"),
    LEGALPERSON(2, "Legal person");

    private int cod;
    private String description;

    public static ClientType toEnum(Integer cod) {
        if (cod == null) return null;
        for (ClientType clientType : ClientType.values())
            if (cod.equals(clientType.getCod()))
                return clientType;
        throw new IllegalStateException("Id inválido: " + cod);
    }
}
