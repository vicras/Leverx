package com.vicras.service;

import com.vicras.dto.UserDTO;
import com.vicras.entity.User;
import com.vicras.exception.CodeNotFoundException;
import com.vicras.exception.UserAlreadyExistException;
import com.vicras.exception.UserNotExistException;

public interface AuthenticationService {
    User addNewUser(UserDTO user) throws UserAlreadyExistException;

    void confirmUser(String code) throws CodeNotFoundException;

    void forgotPasswordWithEmail(String email) throws UserNotExistException;

    void updatePasswordByCode(String code, String newPassword) throws CodeNotFoundException;

    boolean isCodeActive(String code);

}
