package com.example.mwo.app.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {

    @Pattern(regexp = "^[a-zA-Z ]{3,20}$", message = "first Name has " +
            "to have at least 3 characters and no more than 20" +
            "and contain only letters or space")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]{3,30}$", message = "last Name has " +
            "to have at least 3 characters and no more than 30" +
            "and contain only letters")
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "^[a-zA-Z]{3,30}$", message = "password has " +
            "to have at least 3 characters and no more than 30")
    private String password;

    @Pattern(regexp = "^[a-zA-Z]{3,30}$", message = "city has " +
            "to have at least 3 characters and no more than 30" +
            "and contain only letters or space")
    private String city;
}
