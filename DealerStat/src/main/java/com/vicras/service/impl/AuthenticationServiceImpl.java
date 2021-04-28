package com.vicras.service.impl;

import com.vicras.dto.UserDTO;
import com.vicras.entity.EntityStatus;
import com.vicras.entity.User;
import com.vicras.exception.CodeNotFoundException;
import com.vicras.exception.UserAlreadyExistException;
import com.vicras.exception.UserNotExistException;
import com.vicras.repository.UserCodeRepository;
import com.vicras.repository.UserRepository;
import com.vicras.security.UserConfirmMessage;
import com.vicras.security.jwt.JwtTokenProvider;
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
    private long TIMEOUT;

    @Value("${new.user.confirm.link}")
    private String NEW_USER_CONFIRM_LINK;

    @Value("${reset.pass.confirm.link}")
    private String RESET_PASS_CONFIRM_LINK;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final CodeGeneratorService codeGeneratorService;
    private final UserCodeRepository userCodeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     ApplicationEventPublisher publisher,
                                     CodeGeneratorService codeGeneratorService,
                                     UserCodeRepository userCodeRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.publisher = publisher;
        this.codeGeneratorService = codeGeneratorService;
        this.userCodeRepository = userCodeRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(String email, String password) throws UserNotExistException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotExistException::new);
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new UserNotExistException();
        }
        return jwtTokenProvider.createToken(user);
    }

    @Override
    public User addNewUser(UserDTO userDTO) {
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

    public void confirmUser(String code) {
        var userId = userCodeRepository.findWithCode(code)
                .orElseThrow(CodeNotFoundException::new);
        userRepository.findById(userId).ifPresent(user -> {
            if (isUserInactive(user)) {
                activateUser(user);
            }
        });
        userCodeRepository.deleteWithCode(code);
    }

    private boolean isUserInactive(User user) {
        return user.getEntityStatus() == EntityStatus.INACTIVE;
    }

    private void activateUser(User user) {
        user.setEntityStatus(EntityStatus.ACTIVE);
        userRepository.save(user);
    }

    public void forgotPasswordWithEmail(String email) throws UserNotExistException {
        userRepository.findByEmail(email).ifPresentOrElse(user -> {
                    String code = generateCode(user);
                    publicUserCode(user, code);
                    publicResetPasswordConfirmMessageEvent(user, code);
                },
                () -> {
                    throw new UserNotExistException(String.format("User mail: %s not found", email));
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
        userCodeRepository.deleteWithCode(code);
    }

    public boolean isCodeActive(String code) {
        return userCodeRepository.isExistWithCode(code);
    }

}
