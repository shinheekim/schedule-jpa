package com.example.springjpa.user.api;

import com.example.springjpa.global.jwt.TokenProvider;
import com.example.springjpa.user.api.dto.request.UserLoginRequest;
import com.example.springjpa.user.api.dto.response.UserResponse;
import com.example.springjpa.user.api.dto.request.UserSaveRequest;
import com.example.springjpa.user.api.dto.request.UserUpdateRequest;
import com.example.springjpa.user.application.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserSaveRequest request){
        UserResponse response = userService.join(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid UserLoginRequest request,
                                            HttpServletResponse response) {
        String token = userService.loginAndToken(request);
        tokenProvider.addJwtToCookie(token, response);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findOneUser(@PathVariable("userId") Long id) {
        UserResponse response = userService.findOneUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable("userId") Long id,
                                             @RequestBody(required = false) UserUpdateRequest request) {
        userService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
