package com.kevin.courserefactor.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kevin.courserefactor.base.domain.enums.PaymentState;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer paymentState;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "store_order_id")
    @MapsId
    private StoreOrder storeOrder;


    public Payment(Integer id, PaymentState paymentState, StoreOrder storeOrder) {
        this.id = id;
        this.paymentState = paymentState != null ? paymentState.getCod() : null;
        this.storeOrder = storeOrder;
    }


    public PaymentState getPaymentState() {
        return PaymentState.toEnum(paymentState);
    }

    public void setPaymentState(PaymentState paymentState) {
        this.paymentState = paymentState.getCod();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment city = (Payment) o;
        return Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
