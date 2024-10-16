package com.example.springjpa.user.application;

import com.example.springjpa.user.api.dto.response.UserResponse;
import com.example.springjpa.user.api.dto.request.UserSaveRequest;
import com.example.springjpa.user.api.dto.request.UserUpdateRequest;
import com.example.springjpa.user.domain.User;
import com.example.springjpa.user.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(UserSaveRequest request) {
        // 예외처리 - 만약 이미 있는 이메일이라면? => 이미 있는 이메일이라면서 예외처리
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);
        return UserResponse.from(user);
    }

    public UserResponse findOneUser(Long id) {
        User user = findUserById(id);
        return UserResponse.from(user);
    }

    public void update(Long id, UserUpdateRequest request) {
        User user = findUserById(id);
//        if (user.isValidPassword(request.password())) - update, delete 모두 password 일치하는지 확인하는 로직
        // (boolean) passwordEncoder.matches(input, encode)
        user.update(request.name(), request.email());
        userRepository.save(user);
    }

    public void delete(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));
    }
}
