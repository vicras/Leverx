package com.vicras.service.impl;

import com.vicras.entity.EntityStatus;
import com.vicras.entity.User;
import com.vicras.repository.UserRepository;
import com.vicras.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    @Override
    public void addNewUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUsersById(Long userId) {
        userRepository.findById(userId).ifPresent(user ->{
            user.setEntityStatus(EntityStatus.DELETED);
            userRepository.save(user);
        });
    }

    @Override
    public List<User> getActiveUsers() {
         return userRepository.findAllWithStatus(EntityStatus.ACTIVE);
    }

    @Override
    public List<User> getDeletedUsers() {
        return userRepository.findAllWithStatus(EntityStatus.DELETED);
    }
}
