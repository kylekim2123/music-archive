package com.programmers.musicapp.exception;

public class CommentException extends BusinessException {

    public CommentException(ExceptionRule rule, String rejectedValue) {
        super(rule, rejectedValue);
    }
}
