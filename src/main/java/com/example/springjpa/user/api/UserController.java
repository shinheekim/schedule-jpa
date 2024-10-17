package com.example.springjpa.user.api;

import com.example.springjpa.global.jwt.TokenProvider;
import com.example.springjpa.user.api.dto.request.UserLoginRequest;
import com.example.springjpa.user.api.dto.request.UserSaveRequest;
import com.example.springjpa.user.api.dto.request.UserUpdateRequest;
import com.example.springjpa.user.api.dto.response.UserResponse;
import com.example.springjpa.user.application.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserSaveRequest request) {
        UserResponse response = userService.join(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid UserLoginRequest request,
                                            HttpServletResponse response) {
        String token = userService.loginAndGetToken(request);
        tokenProvider.addJwtToCookie(token, response);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserResponse> findOneUser(
        @CookieValue(value = AUTHORIZATION_HEADER) String tokenValue) {
        try {
            Long userId = tokenProvider.getUserIdFromToken(tokenValue);
            UserResponse response = userService.findOneUser(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping
    public ResponseEntity<String> updateUser(@CookieValue(value = AUTHORIZATION_HEADER) String tokenValue,
                                             @RequestBody(required = false) UserUpdateRequest request) {
        try {
            Long userId = tokenProvider.getUserIdFromToken(tokenValue);
            userService.update(userId, request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@CookieValue(value = AUTHORIZATION_HEADER) String tokenValue) {
        try {
            Long userId = tokenProvider.getUserIdFromToken(tokenValue);
            userService.delete(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
