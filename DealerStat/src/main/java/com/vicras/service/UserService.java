package com.vicras.service;

import com.vicras.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void deleteUsersById(Long userId);

    List<User> getActiveUsers();

    List<User> getDeletedUsers();
}
