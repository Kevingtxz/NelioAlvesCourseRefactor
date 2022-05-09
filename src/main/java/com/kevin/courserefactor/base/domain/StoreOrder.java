package com.kevin.courserefactor.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class StoreOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instant;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "storeOrder")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private Address delivery_address;

    @OneToMany(mappedBy = "id.storeOrder")
    private Set<StoreOrderItem> items = new HashSet<>();


    public StoreOrder(Integer id, Date instant, Client client, Address delivery_address) {
        this.id = id;
        this.instant = instant;
        this.client = client;
        this.delivery_address = delivery_address;
    }


    public double getTotalValue() {
        double totalValue = 0d;
        for (StoreOrderItem soi : this.items)
            totalValue += soi.getSubTotal();
        return totalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreOrder city = (StoreOrder) o;
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
        for (StoreOrderItem soi : this.getItems())
            strBuilder.append(soi.toString());
        strBuilder.append("Total value: ");
        strBuilder.append(nf.format(this.getTotalValue()));
        return strBuilder.toString();
    }
}
