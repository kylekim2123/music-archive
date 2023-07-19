package com.programmers.musicapp.dto.request;

import com.programmers.musicapp.entity.Music;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MusicCreateRequest {

    private String title;
    private String posterUrl;
    private String description;
    private String artistName;
    private LocalDate releasedDate;

    public Music toEntity() {
        LocalDateTime nowDateTime = LocalDateTime.now();

        return Music.builder()
                .title(title)
                .posterUrl(posterUrl)
                .description(description)
                .artistName(artistName)
                .releasedDate(releasedDate)
                .createdDatetime(nowDateTime)
                .updatedDateTime(nowDateTime)
                .build();
    }
}
