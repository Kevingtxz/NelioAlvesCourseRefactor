package com.kevin.courserefactor.base.dto.form;

import com.kevin.courserefactor.base.service.validation.ClientInsert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ClientInsert
public class ClientFormNew implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Mandatory completion")
    @Length(min = 5, max = 200, message = "The length must be between 5 and 200 characters")
    private String name;

    @NotEmpty(message = "Mandatory completion")
    @Email(message = "Invalid email")
    private String email;
    @NotEmpty(message = "Mandatory completion")
    private String cpfOrCnpj;
    private Integer clientType;
    @NotEmpty(message = "Mandatory completion")
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumber3;

    @NotEmpty(message = "Mandatory completion")
    private String street;
    @NotEmpty(message = "Mandatory completion")
    private String number;
    private String complement;
    private String neighborhood;
    @NotEmpty(message = "Mandatory completion")
    private String cep;

    private Integer cityId;

}
