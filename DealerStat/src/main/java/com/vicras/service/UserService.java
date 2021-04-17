package com.vicras.service;

import com.vicras.entity.User;

import java.util.List;

public interface UserService {
    void addNewUser(User user);
    void deleteUsersById(Long userId);
    List<User> getActiveUsers();
    List<User> getDeletedUsers();
}
