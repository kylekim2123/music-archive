package com.programmers.musicapp.dto.request;

import com.programmers.musicapp.entity.Music;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MusicUpdateRequest {

    @NotBlank(message = "음악 제목을 입력하세요.")
    private String title;

    @NotBlank(message = "앨범 표지 URL을 입력하세요.")
    private String posterUrl;

    private String description;

    @NotBlank(message = "아티스트 이름을 입력하세요.")
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
