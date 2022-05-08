package com.kevin.courserefactor.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
}
