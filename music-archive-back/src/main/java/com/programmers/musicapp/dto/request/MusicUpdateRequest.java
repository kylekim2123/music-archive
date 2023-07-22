package com.programmers.musicapp.dto.request;

import static com.programmers.musicapp.exception.ExceptionRule.EMPTY_MUSIC_ARTIST_NAME;
import static com.programmers.musicapp.exception.ExceptionRule.EMPTY_MUSIC_POSTER_URL;
import static com.programmers.musicapp.exception.ExceptionRule.EMPTY_MUSIC_TITLE;

import com.programmers.musicapp.entity.Music;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MusicUpdateRequest {

    @NotBlank(message = EMPTY_MUSIC_TITLE)
    private String title;

    @NotBlank(message = EMPTY_MUSIC_POSTER_URL)
    private String posterUrl;

    private String description;

    @NotBlank(message = EMPTY_MUSIC_ARTIST_NAME)
    private String artistName;

    private LocalDate releasedDate;

    public Music toEntity(long id, LocalDateTime createdDatetime) {
        LocalDateTime nowDateTime = LocalDateTime.now();

        return Music.builder()
                .id(id)
                .title(title)
                .posterUrl(posterUrl)
                .description(description)
                .artistName(artistName)
                .releasedDate(releasedDate)
                .createdDatetime(createdDatetime)
                .updatedDatetime(nowDateTime)
                .build();
    }
}
