package com.kevin.courserefactor.base.dto.form;

import com.kevin.courserefactor.base.service.validation.ClientUpdate;
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
@ClientUpdate
public class ClientForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Mandatory completion")
    @Length(min = 5, max = 200, message = "The length must be between 5 and 200 characters")
    private String name;

    @NotEmpty(message = "Mandatory completion")
    @Email(message = "Invalid email")
    private String email;
}
