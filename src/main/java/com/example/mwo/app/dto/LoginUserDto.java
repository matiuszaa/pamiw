package com.example.mwo.app.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginUserDto {
    private String username;
    private String password;
}
