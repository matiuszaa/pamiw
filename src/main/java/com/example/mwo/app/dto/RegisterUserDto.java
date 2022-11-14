package com.example.mwo.app.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterUserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String city;

    private String isDisability;
}
