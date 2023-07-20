package com.programmers.musicapp.dto.request;

import com.programmers.musicapp.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentCreateRequest {

    private String description;

    public Comment toEntity(long musicId) {
        LocalDateTime nowDateTime = LocalDateTime.now();

        return Comment.builder()
                .musicId(musicId)
                .description(description)
                .createdDatetime(nowDateTime)
                .updatedDatetime(nowDateTime)
                .build();
    }
}
