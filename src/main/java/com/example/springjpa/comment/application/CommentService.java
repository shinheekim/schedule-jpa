package com.example.springjpa.comment.application;

import com.example.springjpa.comment.api.dto.request.CommentUpdateRequest;
import com.example.springjpa.comment.api.dto.response.CommentResponse;
import com.example.springjpa.comment.api.dto.request.CommentSaveRequest;
import com.example.springjpa.comment.domain.Comment;
import com.example.springjpa.comment.domain.repository.CommentRepository;
import com.example.springjpa.schedule.domain.Schedule;
import com.example.springjpa.schedule.domain.repository.ScheduleRepository;
import com.example.springjpa.user.domain.User;
import com.example.springjpa.user.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    public CommentResponse create(CommentSaveRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(()-> new EntityNotFoundException("해당 유저 정보가 없습니다." + request.userId()));

        Schedule schedule = scheduleRepository.findById(request.scheduleId())
                .orElseThrow(() -> new EntityNotFoundException("해당 일정 정보가 없습니다." + request.scheduleId()));
        Comment comment = Comment.builder()
                .content(request.content())
                .user(user)
                .schedule(schedule)
                .build();

        commentRepository.save(comment);
        return CommentResponse.from(comment);
    }

    public List<CommentResponse> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    public void update(Long id, CommentUpdateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저의 정보가 없습니다." + request.userId()));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글 정보가 없습니다."+ id));
        comment.update(request.content());
        commentRepository.save(comment);
    }
}
