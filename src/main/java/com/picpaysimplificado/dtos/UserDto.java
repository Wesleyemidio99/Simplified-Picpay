package com.picpaysimplificado.dtos;

import com.picpaysimplificado.domain.user.UserType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserDto {
    private String firstName;
    private String lastName;
    private String document;
    private BigDecimal balance;
    private String email;
    private String password;
    private UserType userType;
}
