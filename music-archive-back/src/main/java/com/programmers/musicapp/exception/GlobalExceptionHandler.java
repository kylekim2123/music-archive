package com.programmers.musicapp.exception;

import static com.programmers.musicapp.exception.ExceptionRule.BAD_REQUEST_400;
import static com.programmers.musicapp.exception.ExceptionRule.INTERNAL_SERVER_ERROR_500;
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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 허용 HTTP Method 예외처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseWrapper<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(METHOD_NOT_ALLOWED_405.getMessage(), e.getMethod());

        return wrapErrorResponse(response, METHOD_NOT_ALLOWED_405.getStatus());
    }

    // PathVariable 타입 예외처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseWrapper<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(NOT_FOUND_404.getMessage(), e.getValue().toString());

        return wrapErrorResponse(response, NOT_FOUND_404.getStatus());
    }

    // 사용자 입력 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<List<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
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

    // 날짜 형식 예외처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        Throwable rootCause = e.getRootCause();
        ErrorResponse response;

        if (rootCause instanceof DateTimeParseException dateTimeParseException) {
            response = ErrorResponse.of(INVALID_RELEASED_DATE_FORMAT, dateTimeParseException.getParsedString());
        } else {
            response = ErrorResponse.from(BAD_REQUEST_400.getMessage());
        }

        return wrapErrorResponse(response, BAD_REQUEST_400.getStatus());
    }

    // 비즈니스 로직 예외처리
    @ExceptionHandler({MusicException.class, CommentException.class})
    public ResponseEntity<ResponseWrapper<ErrorResponse>> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        ExceptionRule rule = e.getRule();
        ErrorResponse response = ErrorResponse.of(rule.getMessage(), e.getRejectedValue());
        ResponseWrapper<ErrorResponse> responseWrapper = wrapErrorResponse(response, rule.getStatus());

        return new ResponseEntity<>(responseWrapper, rule.getStatus());
    }

    // 404 Not Found
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseWrapper<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.from(NOT_FOUND_404.getMessage());

        return wrapErrorResponse(response, NOT_FOUND_404.getStatus());
    }

    // 기타 모든 예외처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public ResponseWrapper<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.from(INTERNAL_SERVER_ERROR_500.getMessage());

        return wrapErrorResponse(response, INTERNAL_SERVER_ERROR_500.getStatus());
    }

    private <T> ResponseWrapper<T> wrapErrorResponse(T response, HttpStatus status) {
        return ResponseWrapper.of(
                status.value(),
                status.getReasonPhrase(),
                response);
    }
}
