package com.example.springjpa.comment.application;

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
                .orElseThrow(() -> new EntityNotFoundException("해당 일정 정보가 없습니다." + request.scheduleId()))
                ;
        Comment comment = Comment.builder()
                .content(request.content())
                .user(user)
                .schedule(schedule)
                .build();

        commentRepository.save(comment);
        return CommentResponse.from(comment);
    }
}
