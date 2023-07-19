package com.programmers.musicapp.dto.request;

import com.programmers.musicapp.entity.Music;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MusicUpdateRequest {

    private String title;
    private String posterUrl;
    private String description;
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
                .updatedDateTime(nowDateTime)
                .build();
    }
}
