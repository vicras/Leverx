package com.vicras.dto;

import com.vicras.entity.EntityStatus;
import com.vicras.entity.Role;
import com.vicras.entity.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    Long id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    String email;
    String firstName;
    String lastName;
    String password;
    Role role;

    public User convert2User(PasswordEncoder encoder) {
        String ePassword = encoder.encode(password);
        var user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(ePassword)
                .role(role)
                .build();
        user.setEntityStatus(EntityStatus.INACTIVE);
        return user;
    }
}
