package com.example.mwo.app;

import com.example.mwo.app.dto.LoginUserDto;
import com.example.mwo.app.dto.RegisterUserDto;
import com.example.mwo.app.entity.User;
import com.example.mwo.app.repository.UserRepository;
import com.example.mwo.app.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    UserServiceImpl userService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepositoryMock);
        assertNotNull(userRepositoryMock);
    }
    UserRepository userRepositoryMock = mock(UserRepository.class, "myMock");
    @Test
    public void ShouldReturnListOfCities() {
        List<String> list = new ArrayList<>();
        list.add("Białystok");
        when(userRepositoryMock.showAvailableCities()).thenReturn(list);

        List<String> result = userService.showAvailableCities();

        assertEquals(result.size(), 1);
        assertEquals(result.get(0), "Białystok");
    }

    @Test
    public void ShouldReturnFoundUserDtoWhenValidUserPassed() {
        LoginUserDto inputUserDto = LoginUserDto.builder()
                .username("example@mail.com")
                .password("password").build();
        User user = User.builder()
                .email(inputUserDto.getUsername())
                .password(inputUserDto.getPassword()).build();
        when(userRepositoryMock.validateUser(any(User.class))).thenReturn(user);

        LoginUserDto result = userService.validateUser(inputUserDto);

        assertEquals(result.getPassword(), inputUserDto.getPassword());
        assertEquals(result.getUsername(), inputUserDto.getUsername());
    }

    @Test
    public void ShouldThrowExceptionForNullInField() {
        LoginUserDto inputUserDto = LoginUserDto.builder()
                .username("example@mail.com")
                .password(null).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser(inputUserDto);
        });

        String expectedMessage = "Not valid Fields";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldRegisterUserProperly() {
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .email("email@example.com")
                .password("password")
                .city("city")
                .firstName("firstName")
                .lastName("lastName").build();

        doNothing().when(userRepositoryMock).registerUser(any(User.class));
    }

    @Test
    public void shouldThrowExceptionWhenUserHasNull() {
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .email("email@example.com")
                .password("password")
                .city(null)
                .firstName("firstName")
                .lastName("lastName").build();

        doNothing().when(userRepositoryMock).registerUser(any(User.class));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(registerUserDto);
        });

        String expectedMessage = "Invalid fields!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
