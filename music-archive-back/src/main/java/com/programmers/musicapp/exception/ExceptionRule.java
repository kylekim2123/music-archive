package com.programmers.musicapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionRule {

    BAD_REQUEST_400(HttpStatus.BAD_REQUEST, "입력 값을 확인해주세요."),
    NOT_FOUND_404(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED_405(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR_500(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다. 이용에 불편을 드려 죄송합니다.");

    public static final String EMPTY_MUSIC_TITLE = "음악 제목을 입력하세요.";
    public static final String EMPTY_MUSIC_POSTER_URL = "앨범 표지 URL을 입력하세요.";
    public static final String EMPTY_MUSIC_ARTIST_NAME = "아티스트 이름을 입력하세요.";
    public static final String EMPTY_COMMENT = "코멘트 내용을 입력하세요.";
    public static final String INVALID_RELEASED_DATE_FORMAT = "발매일의 날짜 형식이 잘못되었습니다.";

    private final HttpStatus status;
    private final String message;
}
