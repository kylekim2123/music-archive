package com.programmers.musicapp.dto.response;

import com.programmers.musicapp.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {

    private long id;
    private long musicId;
    private String description;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public static CommentResponse fromEntity(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .musicId(comment.getMusicId())
                .description(comment.getDescription())
                .createdDatetime(comment.getCreatedDatetime())
                .updatedDatetime(comment.getUpdatedDatetime())
                .build();
    }
}
