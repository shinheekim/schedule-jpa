package com.example.springjpa.comment.api;

import com.example.springjpa.comment.api.dto.request.CommentSaveRequest;
import com.example.springjpa.comment.api.dto.request.CommentUpdateRequest;
import com.example.springjpa.comment.api.dto.response.CommentResponse;
import com.example.springjpa.comment.application.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAllComment() {
        List<CommentResponse> responses = commentService.findAll();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable("commentId") Long id,
                                                @RequestBody @Valid CommentUpdateRequest request) {
        commentService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long id,
                                                @RequestBody Long userId) {
        commentService.delete(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
