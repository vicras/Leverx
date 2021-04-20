package com.vicras.service.impl;

import com.vicras.dto.UserDTO;
import com.vicras.entity.EntityStatus;
import com.vicras.entity.User;
import com.vicras.exception.CodeNotFoundException;
import com.vicras.exception.UserAlreadyExistException;
import com.vicras.repository.UserCodeRepository;
import com.vicras.repository.UserRepository;
import com.vicras.security.UserConfirmMessage;
import com.vicras.service.AuthenticationService;
import com.vicras.service.CodeGeneratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@PropertySource("classpath:application.properties")
public class AuthenticationServiceImpl implements AuthenticationService {

    private final static TimeUnit TIMEOUT_TIME_UNIT = TimeUnit.HOURS;

    @Value("${link.expire.timeinhours}")
    private static long TIMEOUT;

    @Value("${new.user.confirm.link}")
    private static String NEW_USER_CONFIRM_LINK;

    @Value("${reset.pass.confirm.link}")
    private static String RESET_PASS_CONFIRM_LINK;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final CodeGeneratorService codeGeneratorService;
    private final UserCodeRepository userCodeRepository;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     ApplicationEventPublisher publisher,
                                     CodeGeneratorService codeGeneratorService,
                                     UserCodeRepository userCodeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.publisher = publisher;
        this.codeGeneratorService = codeGeneratorService;
        this.userCodeRepository = userCodeRepository;
    }

    @Override
    public User addNewUser(UserDTO userDTO){
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistException(String.format("User with mail %s already exist", userDTO.getEmail()));
        }

        User user = userDTO.convert2User(passwordEncoder);
        userRepository.save(user);

        String code = generateCode(user);
        publicUserCode(user, code);
        publicNewUserConfirmMessageEvent(user, code);
        return user;
    }

    private String generateCode(User user) {
        return codeGeneratorService.generateUniqueCode(user);
    }

    private void publicUserCode(User user, String code) {
        userCodeRepository.saveTemporarily(code, user.getId(), TIMEOUT, TIMEOUT_TIME_UNIT);
    }

    private void publicNewUserConfirmMessageEvent(User user, String code) {
        String confirmLink = NEW_USER_CONFIRM_LINK + code;
        var event = UserConfirmMessage
                .getNewUserMessage(user.getEmail(), confirmLink);
        publisher.publishEvent(event);
    }

    public void confirmUser(String code, User currentUser){
        var userId = userCodeRepository.findWithCode(code)
                .orElseThrow(CodeNotFoundException::new);
        if (isIdOfThisPerson(currentUser, userId)
                && isUserInactive(currentUser)) {

            currentUser.setEntityStatus(EntityStatus.ACTIVE);
            userRepository.save(currentUser);
        }
    }

    private boolean isIdOfThisPerson(User user, Long id) {
        return user.getId().equals(id);
    }

    private boolean isUserInactive(User user) {
        return user.getEntityStatus() == EntityStatus.INACTIVE;
    }


    public void forgotPasswordWithEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            String code = generateCode(user);
            publicUserCode(user, code);
            publicResetPasswordConfirmMessageEvent(user, code);
        });

    }

    private void publicResetPasswordConfirmMessageEvent(User user, String code) {
        var event = UserConfirmMessage
                .getPasswordResetUserMessage(user.getEmail(), RESET_PASS_CONFIRM_LINK, code);
        publisher.publishEvent(event);
    }

    public void updatePasswordByCode(String code, String newPassword) throws CodeNotFoundException {
        Long userId = userCodeRepository.findWithCode(code)
                .orElseThrow(CodeNotFoundException::new);

        userRepository.findById(userId).ifPresent(user -> {
            var ePassword = passwordEncoder.encode(newPassword);
            user.setPassword(ePassword);
            userRepository.save(user);
        });

    }

    public boolean isCodeActive(String code) {
        return userCodeRepository.isExistWithCode(code);
    }

}