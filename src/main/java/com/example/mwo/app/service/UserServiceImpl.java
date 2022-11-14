package com.example.mwo.app.service;

import java.util.List;

import com.example.mwo.app.dto.LoginUserDto;
import com.example.mwo.app.dto.RegisterUserDto;
import com.example.mwo.app.entity.User;
import com.example.mwo.app.factory.UserFactory;
import com.example.mwo.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userDAO;

    private UserFactory userFactory = new UserFactory();

    private User loggedUser;

    @Override
    @Transactional
    public void registerUser(RegisterUserDto theUser) {
        if(theUser.getCity() == null ||  theUser.getPassword() == null || theUser.getEmail() == null) {
            log.error("User cannot be registered because of wrong input");
            throw new IllegalArgumentException("Invalid fields!");
        }
        User registeredUser = userFactory.mapRegisterToUser(theUser);
        userDAO.registerUser(registeredUser);
    }

    @Override
    @Transactional
    public LoginUserDto validateUser(LoginUserDto loggingUser) {
        if (loggingUser.getPassword() == null || loggingUser.getUsername() == null) {
            log.error("Not valid login user");
            throw new IllegalArgumentException("Not valid Fields");
        }

        loggedUser = userFactory.mapLoginToUser(loggingUser);
        User foundUser = userDAO.validateUser(loggedUser);

        return userFactory.mapUserToLogin(foundUser);
    }

    @Override
    @Transactional
    public List<String> showAvailableCities() {
        List<String> locationsList = userDAO.showAvailableCities();
        return locationsList;
    }

}
