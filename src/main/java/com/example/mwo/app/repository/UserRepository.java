package com.example.mwo.app.repository;

import com.example.mwo.app.entity.Role;
import com.example.mwo.app.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRepository {

    void registerUser(User registeredUser);
    User validateUser(User loggingUser);
    List<String> showAvailableCities();

    User saveUserAndRole(User person, Role role);
}
