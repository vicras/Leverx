package com.vicras.controllers;

import com.vicras.dto.EmailPasswordDTO;
import com.vicras.dto.UserDTO;
import com.vicras.exception.CannotSendMessageException;
import com.vicras.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService){
        this.authService = authService;
    }


    @GetMapping("/login")
    private ResponseEntity<String> login(@RequestBody EmailPasswordDTO emailPasswordDTO) {
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
    private ResponseEntity<String> updatePasswordWithCode(@RequestParam String code, @RequestParam String password) {
        authService.updatePasswordByCode(code, password);
        return new ResponseEntity<>("Password was successfully updated", HttpStatus.OK);
    }

    @GetMapping("/check_code")
    private ResponseEntity<String> isCodeValid(@RequestParam String code) {
        if (authService.isCodeActive(code)) {
            return new ResponseEntity<>("Code is active", HttpStatus.OK);
        }
        return new ResponseEntity<>("Code isn't active", HttpStatus.OK);
    }

    @ExceptionHandler(CannotSendMessageException.class)
    public ResponseEntity<String> handleException(CannotSendMessageException e) {
        return new ResponseEntity<>(
                String.format("Server couldn't process request with reason %s, contact with admin", e.getMessage()),
                HttpStatus.OK);
    }

}
