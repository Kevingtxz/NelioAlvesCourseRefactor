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
public class OrderItemPKEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "store_order_id")
    private OrderEntity storeOrder;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPKEntity that = (OrderItemPKEntity) o;
        return Objects.equals(storeOrder, that.storeOrder) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeOrder, product);
    }
}
