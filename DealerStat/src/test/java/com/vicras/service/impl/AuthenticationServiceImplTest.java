package com.vicras.service.impl;

import com.vicras.dto.UserDTO;
import com.vicras.entity.EntityStatus;
import com.vicras.entity.Role;
import com.vicras.entity.User;
import com.vicras.event.UserConfirmMessage;
import com.vicras.exception.CodeNotFoundException;
import com.vicras.exception.UserAlreadyExistException;
import com.vicras.exception.UserNotExistException;
import com.vicras.repository.UserCodeRepository;
import com.vicras.repository.UserRepository;
import com.vicras.service.CodeGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    CodeGeneratorService codeGeneratorService;
    @Mock
    UserCodeRepository userCodeRepository;
    @Mock
    ApplicationEventPublisher publisher;


    @InjectMocks
    AuthenticationServiceImpl authService;
    private final UserDTO userDTO = UserDTO.builder()
            .email("user@gmail.com")
            .lastName("lastName")
            .firstName("firstName")
            .password("password")
            .role(Role.ADMIN)
            .build();

    @Test
    void addExistingUser() {
        var user = User.builder().build();
        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findByEmail("user@gmail.com");
        Assertions.assertThrows(
                UserAlreadyExistException.class,
                () -> authService.addNewUser(userDTO)
        );
    }

    @Test
    void addUser() {
        Mockito.doReturn("encrypted")
                .when(passwordEncoder)
                .encode(any());

        String new_code = "new code";
        User user = userDTO.convert2User(passwordEncoder);
        Mockito.doReturn(new_code)
                .when(codeGeneratorService)
                .generateUniqueCode(user);

        authService.addNewUser(userDTO);
        Mockito.verify(userRepository, Mockito.times(1))
                .save(user);

        Mockito.verify(userCodeRepository, Mockito.times(1))
                .saveTemporarily(
                        eq(new_code),
                        eq(user.getId()),
                        anyLong(),
                        eq(TimeUnit.HOURS));

        Mockito.verify(publisher, Mockito.times(1))
                .publishEvent(any(UserConfirmMessage.class));
    }

    @Test
    void confirmUserWithCode() {
        String code = "code";
        User user = User.builder().build();
        user.setId(1L);
        user.setEntityStatus(EntityStatus.INACTIVE);

        Mockito.doReturn(Optional.of(1L))
                .when(userCodeRepository)
                .findWithCode(code);

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(1L);

        authService.confirmUser(code);
        Assertions.assertEquals(user.getEntityStatus(), EntityStatus.ACTIVE);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void confirmUserWithoutCode() {
        Assertions.assertThrows(
                CodeNotFoundException.class,
                () -> authService.confirmUser("code")
        );

    }

    @Test
    void forgotPasswordWithNotExistEmail() {
        Assertions.assertThrows(
                UserNotExistException.class,
                () -> authService.forgotPasswordWithEmail("mail@gmail.com")
        );
    }

    @Test
    void forgotPasswordWithEmail() {
        String email = "user@gmail.com";
        var user = User.builder()
                .email(email)
                .build();
        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findByEmail(email);
        authService.forgotPasswordWithEmail(email);

        Mockito.verify(userCodeRepository, Mockito.times(1))
                .saveTemporarily(
                        any(),
                        eq(user.getId()),
                        anyLong(),
                        eq(TimeUnit.HOURS));
        Mockito.verify(publisher, Mockito.times(1))
                .publishEvent(any(UserConfirmMessage.class));
    }

    @Test
    void updatePasswordByNotExistingCode() {
        var code = "code";
        var newPass = "new pass";
        Assertions.assertThrows(
                CodeNotFoundException.class,
                () -> authService.updatePasswordByCode(code, newPass)
        );
    }

    @Test
    void updatePasswordByExistingCode() {
        var code = "code";
        var newPass = "new pass";
        Mockito.doReturn(Optional.of(1L))
                .when(userCodeRepository)
                .findWithCode(code);

        User user = User.builder().build();
        user.setId(1L);
        user.setEntityStatus(EntityStatus.INACTIVE);

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(1L);

        Mockito.doReturn(newPass)
                .when(passwordEncoder)
                .encode(eq(newPass));

        authService.updatePasswordByCode(code, newPass);

        Mockito.verify(userCodeRepository, Mockito.times(1)).deleteWithCode(code);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assertions.assertEquals(user.getPassword(), newPass);

    }
}