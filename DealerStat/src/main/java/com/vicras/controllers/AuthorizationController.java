package com.vicras.controllers;

import com.vicras.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
public class AuthorizationController {

    @PostMapping("/sign_up")
    private void registerUser(@RequestBody User user){

    }

    @PostMapping("/confirm/{hash_code}")
    private void registerUser(@PathVariable("hash_code") String hashCode){

    }

    @PostMapping("/forgot_password")
    private void restorePassword(@RequestBody String userEmail){

    }

    @PostMapping("/reset_password")
    private void resetPassword(/*code new password*/){

    }

}
