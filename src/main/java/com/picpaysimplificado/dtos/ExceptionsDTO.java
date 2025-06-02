package com.picpaysimplificado.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionsDTO {
    private String message;
    private String errorCode;
}
