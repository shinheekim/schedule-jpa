package com.example.springjpa.comment.api.dto.response;

import com.example.springjpa.comment.domain.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        String content,
        LocalDateTime createAt,
        LocalDateTime modifiedAt
) {
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .content(comment.getContent())
                .createAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
