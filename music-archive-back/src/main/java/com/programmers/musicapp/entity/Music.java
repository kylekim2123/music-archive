package com.programmers.musicapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Music {

    @Setter
    private long id;

    private String title;
    private String posterUrl;
    private String description;
    private String artistName;
    private LocalDate releasedDate;
    private Boolean isSpotify;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;
}
