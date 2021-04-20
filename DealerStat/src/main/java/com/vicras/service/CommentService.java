package com.vicras.service;

import com.vicras.dto.CommentDTO;
import com.vicras.dto.NewUserWithCommentAndObjectsDTO;
import com.vicras.entity.Comment;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    void addCommentForUserWithId(Long userId, CommentDTO commentDTO);
    void addCommentForNewUserWithObjects(NewUserWithCommentAndObjectsDTO dto);

    List<Comment> getCommentsForUserWithId(Long userId);
    List<Comment> getSortedCommentsForUserWithId(Long userId, Comparator<Comment> comparator, int limit);

    Optional<Comment> getCommentWithId(Long commentId);
}
