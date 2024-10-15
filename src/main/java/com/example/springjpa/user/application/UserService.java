package com.example.springjpa.user.application;

import com.example.springjpa.user.api.dto.UserResponse;
import com.example.springjpa.user.api.dto.UserSaveRequest;
import com.example.springjpa.user.api.dto.UserUpdateRequest;
import com.example.springjpa.user.domain.User;
import com.example.springjpa.user.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserResponse create(UserSaveRequest request) {
        // 예외처리 - 만약 이미 있는 이메일이라면? => 이미 있는 이메일이라면서 예외처리
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .build();
        userRepository.save(user);
        return UserResponse.from(user);
    }

    public UserResponse findOneUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));
        return UserResponse.from(user);
    }

    public void update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));
        user.update(request.name(), request.email());
        userRepository.save(user);
    }
}
