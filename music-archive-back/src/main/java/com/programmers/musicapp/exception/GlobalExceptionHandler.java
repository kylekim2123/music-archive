package com.programmers.musicapp.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.programmers.musicapp.dto.response.ErrorResponse;
import com.programmers.musicapp.dto.response.ResponseWrapper;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<List<ErrorResponse>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorResponse> responses = new ArrayList<>();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

        for (ObjectError error : allErrors) {
            FieldError fieldError = (FieldError) error;
            ErrorResponse response = ErrorResponse.of(
                    fieldError.getDefaultMessage(),
                    fieldError.getRejectedValue().toString());

            responses.add(response);
        }

        ResponseWrapper<List<ErrorResponse>> wrappedResponse = ResponseWrapper.of(
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                responses
        );

        return new ResponseEntity<>(wrappedResponse, BAD_REQUEST);


    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ResponseWrapper<ErrorResponse>> handleMethodArgumentNotValidException(DateTimeParseException e) {
        ErrorResponse response = ErrorResponse.of("발매일의 날짜 형식이 잘못되었습니다.", e.getParsedString());

        ResponseWrapper<ErrorResponse> wrappedResponse = ResponseWrapper.of(
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                response);

        return new ResponseEntity<>(wrappedResponse, BAD_REQUEST);
    }
}
