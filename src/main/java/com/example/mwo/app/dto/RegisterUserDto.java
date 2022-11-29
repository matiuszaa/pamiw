package com.example.mwo.app.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String city;
}
