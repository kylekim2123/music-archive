package com.programmers.musicapp.exception;

import static com.programmers.musicapp.exception.ExceptionRule.BAD_REQUEST_400;
import static com.programmers.musicapp.exception.ExceptionRule.INVALID_RELEASED_DATE_FORMAT;
import static com.programmers.musicapp.exception.ExceptionRule.METHOD_NOT_ALLOWED_405;
import static com.programmers.musicapp.exception.ExceptionRule.NOT_FOUND_404;

import com.programmers.musicapp.dto.response.ErrorResponse;
import com.programmers.musicapp.dto.response.ResponseWrapper;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseWrapper<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ErrorResponse response = ErrorResponse.of(METHOD_NOT_ALLOWED_405.getMessage(), e.getMethod());

        return wrapErrorResponse(response, METHOD_NOT_ALLOWED_405.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseWrapper<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorResponse response = ErrorResponse.of(NOT_FOUND_404.getMessage(), e.getValue().toString());

        return wrapErrorResponse(response, NOT_FOUND_404.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<List<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorResponse> responses = new ArrayList<>();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

        for (ObjectError error : allErrors) {
            FieldError fieldError = (FieldError) error;

            ErrorResponse response = ErrorResponse.of(
                    fieldError.getDefaultMessage(),
                    fieldError.getRejectedValue().toString());

            responses.add(response);
        }

        return wrapErrorResponse(responses, BAD_REQUEST_400.getStatus());
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<ErrorResponse> handleMethodArgumentNotValidException(DateTimeParseException e) {
        ErrorResponse response = ErrorResponse.of(INVALID_RELEASED_DATE_FORMAT, e.getParsedString());

        return wrapErrorResponse(response, BAD_REQUEST_400.getStatus());
    }

    @ExceptionHandler({MusicException.class, CommentException.class})
    public ResponseEntity<ResponseWrapper<ErrorResponse>> handleBusinessException(BusinessException e) {
        ExceptionRule rule = e.getRule();
        ErrorResponse response = ErrorResponse.of(rule.getMessage(), e.getRejectedValue());
        ResponseWrapper<ErrorResponse> responseWrapper = wrapErrorResponse(response, rule.getStatus());

        return new ResponseEntity<>(responseWrapper, rule.getStatus());
    }

    private <T> ResponseWrapper<T> wrapErrorResponse(T response, HttpStatus status) {
        return ResponseWrapper.of(
                status.value(),
                status.getReasonPhrase(),
                response
        );
    }
}
