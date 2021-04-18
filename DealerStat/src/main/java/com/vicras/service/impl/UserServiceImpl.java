package com.vicras.service.impl;

import com.vicras.entity.EntityStatus;
import com.vicras.entity.User;
import com.vicras.repository.UserRepository;
import com.vicras.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElseThrow(
                () -> new UsernameNotFoundException(String.format("User '%s' not found", email)));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getUserCredentials(user)
                );
    }

    private Collection<? extends GrantedAuthority> getUserCredentials(User user){
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));

    }
}
