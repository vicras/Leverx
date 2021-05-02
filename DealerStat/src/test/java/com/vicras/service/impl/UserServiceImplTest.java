package com.vicras.service.impl;

import com.vicras.config.SecurityConfig;
import com.vicras.config.WebConfig;
import com.vicras.entity.Role;
import com.vicras.entity.User;
import com.vicras.exception.UserNotExistException;
import com.vicras.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {WebConfig.class, SecurityConfig.class})
@WebAppConfiguration
class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loadNotExistUserByUsername() {
        var username = "user@mail.com";

        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(username)
        );
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(username);
    }

    @Test
    void loadUserByUsername() {
        var username = "user@mail.com";
        var user = User.builder()
                .email(username)
                .password("pass")
                .firstName("first name")
                .lastName("last name")
                .role(Role.ADMIN)
                .build();

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findByEmail(username);
        UserDetails userDetails = userService.loadUserByUsername(username);
        Assertions.assertEquals(user.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(user.getEmail(), userDetails.getUsername());
    }

    @Test
    void getNotExistUserByEmail() {
        var mail = "user@gmail.com";
        Assertions.assertThrows(
                UserNotExistException.class,
                () -> userService.getUserByEmail(mail)
        );
    }

}