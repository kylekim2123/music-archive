package com.programmers.musicapp.dto.response;

import com.programmers.musicapp.entity.Music;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicResponse {

    private long id;
    private String title;
    private String posterUrl;
    private String description;
    private String artistName;
    private LocalDate releasedDate;
    private Boolean isSpotify;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public static MusicResponse fromEntity(Music music) {
        return MusicResponse.builder()
                .id(music.getId())
                .title(music.getTitle())
                .posterUrl(music.getPosterUrl())
                .description(music.getDescription())
                .artistName(music.getArtistName())
                .releasedDate(music.getReleasedDate())
                .isSpotify(music.getIsSpotify())
                .createdDatetime(music.getCreatedDatetime())
                .updatedDatetime(music.getUpdatedDatetime())
                .build();
    }
}
