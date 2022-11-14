package com.example.mwo.app.service;

import java.util.List;

import com.example.mwo.app.dto.LoginUserDto;
import com.example.mwo.app.dto.RegisterUserDto;
import com.example.mwo.app.entity.User;

public interface UserService {

    public void registerUser(RegisterUserDto theUser);

    public LoginUserDto validateUser(LoginUserDto loggingUser);

    public List<String> showAvailableCities();
}
