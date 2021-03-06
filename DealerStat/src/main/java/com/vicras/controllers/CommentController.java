package com.vicras.controllers;

import com.vicras.dto.CommentDTO;
import com.vicras.dto.NewUserWithCommentAndObjectsDTO;
import com.vicras.entity.Comment;
import com.vicras.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/comment")
@RestController
public class CommentController {

    private final static String DEFAULT_AMOUNTS = "2147483647";
    public final static Comparator<Comment> BY_SCORE_COMPARATOR = Comparator.comparingInt(Comment::getMark);
    public final static Comparator<Comment> BY_SCORE_REVERSE_COMPARATOR = BY_SCORE_COMPARATOR.reversed();

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/user/{id}")
    private List<CommentDTO> getCommentsAboutUserWithId(@PathVariable("id") long userId) {
        return commentService.getCommentsForUserWithId(userId).stream()
                .map(Comment::convert2DTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/user/{id}")
    private ResponseEntity<String> addCommentForUserWithId(
            @RequestBody CommentDTO commentDTO,
            @PathVariable(name = "id") Long userId) {
        commentService.addCommentForUserWithId(userId, commentDTO);
        return new ResponseEntity<>("add successfully", HttpStatus.OK);
    }

    @GetMapping("/user/{id}/best")
    private List<CommentDTO> getBestCommentsAboutUserWithId(
            @PathVariable("id") long userId,
            @RequestParam(required = false, defaultValue = DEFAULT_AMOUNTS) String amount) {
        int limit = Integer.parseInt(amount);
        if (limit < 0) return Collections.emptyList();
        List<Comment> comments = commentService.getSortedCommentsForUserWithId(
                userId,
                BY_SCORE_REVERSE_COMPARATOR,
                limit);
        return convert2DTOList(comments);
    }

    @GetMapping("/user/{id}/worst")
    private List<CommentDTO> getWorstCommentsAboutUserWithId(
            @PathVariable("id") long userId,
            @RequestParam(required = false, defaultValue = DEFAULT_AMOUNTS) String amount) {
        int limit = Integer.parseInt(amount);
        if (limit < 0) return Collections.emptyList();
        List<Comment> comments = commentService.getSortedCommentsForUserWithId(
                userId,
                BY_SCORE_COMPARATOR,
                limit);
        return convert2DTOList(comments);
    }

    @GetMapping("/{id}")
    private ResponseEntity<CommentDTO> getCommentWithId(@PathVariable("id") long commentId) {
        return commentService.getCommentWithId(commentId)
                .map(Comment::convert2DTO)
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    private ResponseEntity<String> addCommentForNewUser(@RequestBody NewUserWithCommentAndObjectsDTO dto) {
        commentService.addCommentForNewUserWithObjects(dto);
        return new ResponseEntity<>("add successfully", HttpStatus.OK);
    }

    private List<CommentDTO> convert2DTOList(List<Comment> comments) {
        return comments.stream()
                .map(Comment::convert2DTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/for_approve")
    private List<CommentDTO> objectsForApprove() {
        return commentService.getCommentsForApprove().stream()
                .map(Comment::convert2DTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/approve")
    private ResponseEntity<String> approveWithIds(@RequestBody List<Long> idsToApprove) {
        commentService.approveObjects(idsToApprove);
        return new ResponseEntity<>("Comments which has no [approve/decline] status successfully approved", HttpStatus.OK);
    }

    @PostMapping("/decline")
    private ResponseEntity<String> declineWithIds(@RequestBody List<Long> idsToApprove) {
        commentService.declineObjects(idsToApprove);
        return new ResponseEntity<>("Comments which has no [approve/decline] status successfully declined", HttpStatus.OK);
    }


}
