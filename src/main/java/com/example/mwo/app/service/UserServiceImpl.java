package com.example.mwo.app.service;

import java.util.List;

import com.example.mwo.app.dto.LoginUserDto;
import com.example.mwo.app.dto.RegisterUserDto;
import com.example.mwo.app.entity.Role;
import com.example.mwo.app.entity.User;
import com.example.mwo.app.factory.UserFactory;
import com.example.mwo.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userDAO;

    private UserFactory userFactory = new UserFactory();

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
        User loggedUsers = userFactory.mapLoginToUser(loggingUser);
        User foundUser = userDAO.validateUser(loggedUsers);

        return userFactory.mapUserToLogin(foundUser);
    }

    @Override
    public void sendEmail() {
        log.info("email sent!");
    }

    @Override
    @Transactional
    public List<String> showAvailableCities() {
        List<String> locationsList = userDAO.showAvailableCities();
        return locationsList;
    }

    @Override
    public boolean createNewPerson(RegisterUserDto person){
        User user = userFactory.mapRegisterToUser(person);
        boolean isSaved = false;
        Role role = Role.USER;
        User foundUser = userDAO.saveUserAndRole(user, role);
        if (null != foundUser && foundUser.getId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }


}
