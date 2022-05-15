package com.kevin.courserefactor.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instant;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "storeOrder")
    private PaymentEntity payment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private AddressEntity delivery_address;

    @OneToMany(mappedBy = "id.storeOrder")
    private Set<OrderItemEntity> items = new HashSet<>();


    public OrderEntity(Integer id, Date instant, ClientEntity client, AddressEntity delivery_address) {
        this.id = id;
        this.instant = instant;
        this.client = client;
        this.delivery_address = delivery_address;
    }


    public double getTotalValue() {
        double totalValue = 0d;
        for (OrderItemEntity soi : this.items)
            totalValue += soi.getSubTotal();
        return totalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity city = (OrderEntity) o;
        return Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("StoreOrder number: ");
        strBuilder.append(this.getId());
        strBuilder.append(", Instant: ");
        strBuilder.append(sdf.format(this.getInstant()));
        strBuilder.append(", Client: ");
        strBuilder.append(this.getClient().getName());
        strBuilder.append(", Payment situation: ");
        strBuilder.append(this.getPayment().getPaymentState().getDescription());
        strBuilder.append("\nDetails:\n");
        for (OrderItemEntity soi : this.getItems())
            strBuilder.append(soi.toString());
        strBuilder.append("Total value: ");
        strBuilder.append(nf.format(this.getTotalValue()));
        return strBuilder.toString();
    }
}
