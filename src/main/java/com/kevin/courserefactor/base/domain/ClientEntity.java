package com.kevin.courserefactor.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kevin.courserefactor.base.domain.enums.ClientType;
import com.kevin.courserefactor.base.domain.enums.ProfileRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Entity
public class ClientEntity implements Serializable {
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

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="PROFILE_ROLES")
    private Set<Integer> profileRoles = new HashSet<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<OrderEntity> storeOrders = new ArrayList<>();


    public ClientEntity() {
        this.profileRoles.add(ProfileRole.CLIENT.getCod());
    }

    public ClientEntity(Integer id, String name, String email, String cpfOrCnpj, ClientType clientType, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpfOrCnpj = cpfOrCnpj;
        this.clientType = clientType != null ? clientType.getCod() : null;
        this.profileRoles.add(ProfileRole.CLIENT.getCod());
        this.password = password;
    }


    public ClientType getClientType() {
        return ClientType.toEnum(clientType);
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType.getCod()   ;
    }

    public Set<ProfileRole> getProfileRoles() {
        return this.profileRoles
                .stream()
                .map(e -> ProfileRole.toEnum(e))
                .collect(Collectors.toSet());
    }

    public void addProfileRole(ProfileRole profileRole) {
        this.profileRoles.add(profileRole.getCod());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity city = (ClientEntity) o;
        return Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
