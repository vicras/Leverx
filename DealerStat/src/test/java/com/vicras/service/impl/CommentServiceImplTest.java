package com.vicras.service.impl;

import com.vicras.dto.CommentDTO;
import com.vicras.dto.NewUserWithCommentAndObjectsDTO;
import com.vicras.dto.UserDTO;
import com.vicras.entity.Comment;
import com.vicras.entity.Role;
import com.vicras.entity.User;
import com.vicras.exception.ForbiddenAddCommentToAdminException;
import com.vicras.exception.UserNotExistException;
import com.vicras.repository.CommentRepository;
import com.vicras.repository.UserRepository;
import com.vicras.service.AuthenticationService;
import com.vicras.service.GameObjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    GameObjectService gameObjectService;
    @Mock
    CommentRepository commentRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    AuthenticationService authenticationService;


    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    void addCommentForNewUserAdminWithObjects() {
        var userAdmin = UserDTO.builder().role(Role.ADMIN).build();
        var mock = Mockito.mock(NewUserWithCommentAndObjectsDTO.class);

        Mockito.doReturn(userAdmin)
                .when(mock)
                .getUser();

        Assertions.assertThrows(
                ForbiddenAddCommentToAdminException.class,
                ()-> commentService.addCommentForNewUserWithObjects(mock)
        );
    }


    @Test
    void addCommentForUserWithNotExistId() {
        var comment = new CommentDTO();
        Assertions.assertThrows(
                UserNotExistException.class,
                () -> commentService.addCommentForUserWithId(1L, comment)
        );
    }

    @Test
    void addCommentForUserWithAdminId() {
        var userAdmin = User.builder().role(Role.ADMIN).build();
        Mockito.doReturn(Optional.of(userAdmin))
                .when(userRepository)
                .findById(eq(1L));
        var comment = new CommentDTO();

        Assertions.assertThrows(
                ForbiddenAddCommentToAdminException.class,
                ()->commentService.addCommentForUserWithId(1L, comment)
        );
    }

    @Test
    void addCommentForUserWithId() {
        var user = User.builder().role(Role.TRADER).build();
        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(eq(1L));
        var comment = new CommentDTO();

        commentService.addCommentForUserWithId(1L, comment);
        Mockito.verify(commentRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Comment.class));
    }

}