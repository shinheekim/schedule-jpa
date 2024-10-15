package com.example.springjpa.user.api;

import com.example.springjpa.user.api.dto.UserResponse;
import com.example.springjpa.user.api.dto.UserSaveRequest;
import com.example.springjpa.user.application.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserSaveRequest request){
        UserResponse response = userService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findOneUser(@PathVariable("userId") Long userId) {
        UserResponse response = userService.findOneUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
