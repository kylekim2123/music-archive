package com.programmers.musicapp.dto.request;

import com.programmers.musicapp.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentCreateRequest {

    @NotBlank(message = "코멘트 내용을 입력하세요.")
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
