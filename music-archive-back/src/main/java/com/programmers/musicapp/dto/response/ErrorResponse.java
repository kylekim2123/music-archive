package com.programmers.musicapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private String errorMessage;
    private String rejectedValue;

    public static ErrorResponse from(String errorMessage) {
        return new ErrorResponse(errorMessage, "");
    }

    public static ErrorResponse of(String errorMessage, String rejectedValue) {
        return new ErrorResponse(errorMessage, rejectedValue);
    }
}
