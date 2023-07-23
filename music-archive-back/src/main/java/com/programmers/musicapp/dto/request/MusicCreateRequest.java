package com.programmers.musicapp.dto.request;

import static com.programmers.musicapp.exception.ExceptionRule.EMPTY_MUSIC_ARTIST_NAME;
import static com.programmers.musicapp.exception.ExceptionRule.EMPTY_MUSIC_POSTER_URL;
import static com.programmers.musicapp.exception.ExceptionRule.EMPTY_MUSIC_TITLE;

import com.programmers.musicapp.entity.Music;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicCreateRequest {

    @NotBlank(message = EMPTY_MUSIC_TITLE)
    private String title;

    @NotBlank(message = EMPTY_MUSIC_POSTER_URL)
    private String posterUrl;

    private String description;

    @NotBlank(message = EMPTY_MUSIC_ARTIST_NAME)
    private String artistName;

    private LocalDate releasedDate;

    @NotNull
    private Boolean isSpotify;

    public Music toEntity() {
        LocalDateTime nowDateTime = LocalDateTime.now();

        return Music.builder()
                .title(title)
                .posterUrl(posterUrl)
                .description(description)
                .artistName(artistName)
                .releasedDate(releasedDate)
                .isSpotify(isSpotify)
                .createdDatetime(nowDateTime)
                .updatedDatetime(nowDateTime)
                .build();
    }
}
