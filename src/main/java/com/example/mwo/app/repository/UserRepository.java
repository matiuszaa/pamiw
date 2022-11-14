package com.example.mwo.app.repository;

import com.example.mwo.app.entity.User;

import java.util.List;

public interface UserRepository {
    public void registerUser(User registeredUser);

    public User validateUser(User loggingUser);

    public List<String> showAvailableCities();
}
