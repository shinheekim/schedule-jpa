package com.example.springjpa.comment.api.dto.request;

public record CommentUpdateRequest(
        Long userId,
        String content
) {
}
