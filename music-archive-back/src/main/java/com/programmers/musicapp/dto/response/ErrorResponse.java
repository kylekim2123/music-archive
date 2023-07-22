package com.programmers.musicapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private String message;
    private String causeInput;

    public static ErrorResponse of(String message, String causeInput) {
        return new ErrorResponse(message, causeInput);
    }
}
