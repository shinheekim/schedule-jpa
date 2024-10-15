package com.example.springjpa.comment.api;

import com.example.springjpa.comment.api.dto.response.CommentResponse;
import com.example.springjpa.comment.api.dto.request.CommentSaveRequest;
import com.example.springjpa.comment.application.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid CommentSaveRequest request) {
        CommentResponse response = commentService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
