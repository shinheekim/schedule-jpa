package com.example.springjpa.user.application;

import com.example.springjpa.global.exception.ErrorCode;
import com.example.springjpa.global.exception.InvalidCredentialsException;
import com.example.springjpa.global.exception.UserNotFoundException;
import com.example.springjpa.global.jwt.TokenProvider;
import com.example.springjpa.user.api.dto.request.UserLoginRequest;
import com.example.springjpa.user.api.dto.request.UserSaveRequest;
import com.example.springjpa.user.api.dto.request.UserUpdateRequest;
import com.example.springjpa.user.api.dto.response.UserResponse;
import com.example.springjpa.user.domain.Role;
import com.example.springjpa.user.domain.User;
import com.example.springjpa.user.domain.repository.UserRepository;
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
    private final TokenProvider tokenProvider;

    public UserResponse join(UserSaveRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserNotFoundException(ErrorCode.USER_ALREADY_EXIST.getMessage());
        }

        User user = User.builder()
                .name(request.name())
                .nickname(request.nickname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);
        return UserResponse.from(user);
    }

    public String loginAndGetToken(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return tokenProvider.createToken(user.getName(), user.getRole());
    }

    public UserResponse findOneUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage())
        );
        return UserResponse.from(user);
    }

    public void update(String email, UserUpdateRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage())
        );
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        user.update(request.name(), request.email());
        userRepository.save(user);
    }

    public void delete(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage())
        );
        userRepository.delete(user);
    }
}
