package com.programmers.musicapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseWrapper<T> {

    private int status;
    private String message;
    private T data;

    public static <T> ResponseWrapper<T> of(int status, String message, T data) {
        return new ResponseWrapper<>(status, message, data);
    }
}
