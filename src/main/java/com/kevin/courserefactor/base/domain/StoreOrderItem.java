package com.kevin.courserefactor.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class StoreOrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private StoreOrderItemPK id = new StoreOrderItemPK();
    private Double discount;
    private Integer quantity;
    private Double price;


    public StoreOrderItem(StoreOrder storeOrder, Product product,Double discount, Integer quantity, Double price) {
        this.id.setStoreOrder(storeOrder);
        this.id.setProduct(product);
        this.discount = discount;
        this.quantity = quantity;
        this.price = price;
    }


    public double getSubTotal() {
        return (this.price - this.discount) * this.quantity;
    }

    @JsonIgnore
    public StoreOrder getStoreOrder() {
        return this.id.getStoreOrder();
    }

    public void setStoreOrder(StoreOrder storeOrder) {
        this.id.setStoreOrder(storeOrder);
    }

    public Product getProduct() {
        return this.id.getProduct();
    }

    public void setProduct(Product product) {
        this.id.setProduct(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreOrderItem that = (StoreOrderItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(this.getProduct().getName());
        strBuilder.append(", Qtd: ");
        strBuilder.append(this.getQuantity());
        strBuilder.append(", Unit price: ");
        strBuilder.append(nf.format(this.getPrice()));
        strBuilder.append(", Subtotal: ");
        strBuilder.append(nf.format(this.getSubTotal()));
        strBuilder.append("\n");
        return strBuilder.toString();
    }
}
