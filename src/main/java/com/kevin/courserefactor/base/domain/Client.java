package com.kevin.courserefactor.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kevin.courserefactor.base.domain.enums.ClientType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;
    private String cpfOrCnpj;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer clientType;
    @JsonIgnore
    private String password;

    @ElementCollection
    @CollectionTable(name="PHONE_NUMBER")
    private Set<String> phoneNumbers = new HashSet<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<StoreOrder> storeOrders = new ArrayList<>();


    public Client(Integer id, String name, String email, String cpfOrCnpj, ClientType clientType, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpfOrCnpj = cpfOrCnpj;
        this.clientType = clientType != null ? clientType.getCod() : null;
        this.password = password;
    }


    public ClientType getClientType() {
        return ClientType.toEnum(clientType);
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType.getCod()   ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client city = (Client) o;
        return Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
