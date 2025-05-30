package com.picpaysimplificado.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDto {
    private String email;
    private String message;
}
