package com.vicras.entity;

import com.vicras.dto.UserDTO;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(callSuper = true,exclude = {"gameObjects", "comments"})
@ToString(exclude = {"gameObjects", "comments"} )
@Data
@Entity
@Builder
@AllArgsConstructor
@Transactional
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

    @OneToMany(mappedBy = "destinationUser")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "owner")
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
