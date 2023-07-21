package com.programmers.musicapp.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Comment {

    @Setter
    private long id;

    private long musicId;
    private String description;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;
}
