package com.vicras.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class UserDTO {
    Long id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    String email;
    String firstName;
    String lastName;
    String password;
    String role;
}
