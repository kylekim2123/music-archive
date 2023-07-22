package com.programmers.musicapp.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ExceptionRule rule;
    private final String rejectedValue;

    protected BusinessException(ExceptionRule rule, String rejectedValue) {
        super(rule.getMessage());
        this.rule = rule;
        this.rejectedValue = rejectedValue;
    }
}
