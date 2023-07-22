package com.programmers.musicapp.exception;

public class MusicException extends BusinessException {

    public MusicException(ExceptionRule rule, String rejectedValue) {
        super(rule, rejectedValue);
    }
}
