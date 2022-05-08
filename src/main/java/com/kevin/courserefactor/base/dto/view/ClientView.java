package com.kevin.courserefactor.base.dto.view;

import com.kevin.courserefactor.base.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientView {

    private Integer id;
    private String name;
    private String email;

    public ClientView(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
    }
}
