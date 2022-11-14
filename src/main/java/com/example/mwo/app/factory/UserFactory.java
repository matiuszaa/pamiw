package com.example.mwo.app.factory;

import com.example.mwo.app.dto.LoginUserDto;
import com.example.mwo.app.dto.RegisterUserDto;
import com.example.mwo.app.entity.User;

public class UserFactory {

    public User mapLoginToUser(LoginUserDto loginUserDto) {
        return User.builder()
                .email(loginUserDto.getUsername())
                .password(loginUserDto.getPassword()).build();
    }

    public LoginUserDto mapUserToLogin(User loginUserDto) {
        return LoginUserDto.builder()
                .username(loginUserDto.getEmail())
                .password(loginUserDto.getPassword()).build();
    }

    public User mapRegisterToUser(RegisterUserDto loginUserDto) {
        return User.builder()
                .email(loginUserDto.getEmail())
                .password(loginUserDto.getPassword())
                .city(loginUserDto.getCity()).build();
    }

    public RegisterUserDto mapUserToRegister(User loginUserDto) {
        return RegisterUserDto.builder()
                .email(loginUserDto.getEmail())
                .password(loginUserDto.getPassword())
                .city(loginUserDto.getCity()).build();
    }
}
