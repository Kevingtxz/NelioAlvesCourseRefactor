package com.kevin.courserefactor.base.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@Embeddable
public class StoreOrderItemPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "store_order_id")
    private StoreOrder storeOrder;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreOrderItemPK that = (StoreOrderItemPK) o;
        return Objects.equals(storeOrder, that.storeOrder) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeOrder, product);
    }
}
