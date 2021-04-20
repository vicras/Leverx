package com.vicras.entity;

import com.vicras.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
public class User extends BaseEntity {

    @Transient
    private static final String PASSWORD_PLACEHOLDER = "*****";

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "destinationUser", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<GameObject> gameObjects;

    public User() {
    }

    public UserDTO convert2DTO() {
        return UserDTO.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .email(email)
                .password(PASSWORD_PLACEHOLDER)
                .lastName(lastName)
                .firstName(firstName)
                .role(role)
                .build();
    }
}
