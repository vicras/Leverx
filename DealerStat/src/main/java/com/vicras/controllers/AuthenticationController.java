package com.vicras.controllers;

import com.vicras.dto.CodePasswordDTO;
import com.vicras.dto.EmailPasswordDTO;
import com.vicras.dto.UserDTO;
import com.vicras.service.AuthenticationService;
import com.vicras.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authService,
                                    UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }


    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody EmailPasswordDTO emailPasswordDTO, HttpServletResponse response) {
        try {
            String token = authService.login(emailPasswordDTO.getEmail(), emailPasswordDTO.getPassword());
            return new ResponseEntity<>("JWT: " + token, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("Login or password incorrect", HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/sign_up")
    private ResponseEntity<String> registerUser(@RequestBody UserDTO user) {
        authService.addNewUser(user);
        return new ResponseEntity<>("Registered successfully, check your mail to confirm account", HttpStatus.OK);
    }

    @GetMapping("/confirm/{hash_code}")
    private ResponseEntity<String> confirmUser(@PathVariable("hash_code") String code) {
        authService.confirmUser(code);
        return new ResponseEntity<>("Confirmed successfully", HttpStatus.OK);
    }

    @PostMapping("/forgot_password")
    private ResponseEntity<String> forgotPassword(@RequestBody String userEmail) {
        authService.forgotPasswordWithEmail(userEmail);
        return new ResponseEntity<>("Message with instructions has been sent to your mail", HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    private ResponseEntity<String> updatePasswordWithCode(@RequestBody CodePasswordDTO dto) {
        authService.updatePasswordByCode(dto.getCode(), dto.getPassword());
        return new ResponseEntity<>("Password was successfully updated", HttpStatus.OK);
    }

    @GetMapping("/check_code")
    private ResponseEntity<String> isCodeValid(@RequestParam String code) {
        if (authService.isCodeActive(code)) {
            return new ResponseEntity<>("Code is active", HttpStatus.OK);
        }
        return new ResponseEntity<>("Code isn't active", HttpStatus.OK);
    }

}
