package com.vicras.controllers;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment")
@RestController
public class CommentController {

    @GetMapping("/user/{id}")
    private void getCommentsAboutUserWithId(@PathVariable("id") long userId){

    }

    @GetMapping("/user/{id}/best")
    private String getBestCommentsAboutUserWithId(@PathVariable("id") long userId, @RequestParam(required = false) String amount){
        return "best comments user id "+ userId + "  with amounts " + amount;
    }

    @GetMapping("/user/{id}/worst")
    private String getWorstCommentsAboutUserWithId(@PathVariable("id") long userId, @RequestParam(required = false) String amount){
        return "worst comments user id "+ userId + "  with amounts " + amount;
    }

    @GetMapping("/{id}")
    private void getCommentWithId(@PathVariable("id") long commentId){

    }



}
