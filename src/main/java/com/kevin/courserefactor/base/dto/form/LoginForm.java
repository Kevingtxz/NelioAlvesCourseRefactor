package com.kevin.courserefactor.base.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
}
