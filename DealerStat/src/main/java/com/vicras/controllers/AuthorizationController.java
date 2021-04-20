package com.vicras.controllers;

import com.vicras.dto.CodePasswordDTO;
import com.vicras.dto.UserDTO;
import com.vicras.entity.User;
import com.vicras.service.AuthenticationService;
import com.vicras.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController()
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthenticationService authService;
    private final UserService userService;

    public AuthorizationController(AuthenticationService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/sign_up")
    private ResponseEntity<String> registerUser(@RequestBody UserDTO user){
        authService.addNewUser(user);
        return new ResponseEntity<>("Registered successfully, check your mail to confirm account",  HttpStatus.OK);
    }

    @PostMapping("/confirm/{hash_code}")
    private ResponseEntity<String> confirmUser(@PathVariable("hash_code") String code, Principal principal){
        User currentUser = getCurrentUser(principal);
        authService.confirmUser(code, currentUser);
        return new ResponseEntity<>("Confirmed successfully",  HttpStatus.OK);
    }

    private User getCurrentUser(Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    @PostMapping("/forgot_password")
    private ResponseEntity<String> forgotPassword(@RequestBody String userEmail){
        authService.forgotPasswordWithEmail(userEmail);
        return new ResponseEntity<>("Message with instructions has been sent to your mail", HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    private ResponseEntity<String> updatePasswordWithCode(@RequestBody CodePasswordDTO dto){
        authService.updatePasswordByCode(dto.getCode(), dto.getPassword());
        return new ResponseEntity<>("Password was successfully updated", HttpStatus.OK);
    }

    @GetMapping("/check_code")
    private ResponseEntity<String> isCodeValid(@RequestParam String code){
        if(authService.isCodeActive(code)){
            return new ResponseEntity<>("Code is active", HttpStatus.OK);
        }
        return new ResponseEntity<>("Code isn't active", HttpStatus.OK);
    }

}
