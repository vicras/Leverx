package com.vicras.service.impl;

import com.vicras.dto.CommentDTO;
import com.vicras.dto.NewUserWithCommentAndObjectsDTO;
import com.vicras.entity.Comment;
import com.vicras.entity.User;
import com.vicras.exception.UserNotExistException;
import com.vicras.repository.CommentRepository;
import com.vicras.repository.UserRepository;
import com.vicras.service.CommentService;
import com.vicras.service.GameObjectService;
import com.vicras.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    public final static Comparator<Comment> BY_SCORE_COMPARATOR = Comparator.comparingInt(Comment::getMark);
    public final static Comparator<Comment> BY_SCORE_REVERSE_COMPARATOR = BY_SCORE_COMPARATOR.reversed();

    final private CommentRepository commentRepository;
    final private UserRepository userRepository;

    final private UserService userService;
    final private GameObjectService gameObjectService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              UserRepository userRepository,
                              UserService userService,
                              GameObjectService gameObjectService) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.gameObjectService = gameObjectService;
    }

    @Override
    public void addCommentForNewUserWithObjects(NewUserWithCommentAndObjectsDTO dto) {
        var destUser = userService.addNewUser(dto.getUser());
        dto.getObjectDTOS().forEach(gameObjectDTO ->
            gameObjectService.addNewGameObjectForUser(gameObjectDTO, destUser)
        );
        addCommentForUser(dto.getCommentDTO(), destUser);
    }

    @Override
    public void addCommentForUserWithId(Long userId, CommentDTO commentDTO) {
        User destUser = getUserIfExistById(userId);
        addCommentForUser(commentDTO, destUser);
    }

    private void addCommentForUser(CommentDTO commentDTO, User destUser) {
        var comment = commentDTO.convert2Comment(destUser);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsForUserWithId(Long userId) {
        User destUser = getUserIfExistById(userId);
        return commentRepository.findAllByDestinationUser(destUser);
    }

    private User getUserIfExistById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotExistException("Can't find user with id " + userId)
        );
    }

    @Override
    public List<Comment> getSortedCommentsForUserWithId(
            Long userId,
            Comparator<Comment> comparator,
            int limit) {
        return getCommentsForUserWithId(userId).stream()
                .sorted(comparator)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Comment> getCommentWithId(Long commentId) {
        return commentRepository.findById(commentId);
    }
}
