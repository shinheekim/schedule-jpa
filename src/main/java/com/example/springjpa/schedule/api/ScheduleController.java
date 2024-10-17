package com.example.springjpa.schedule.api;

import com.example.springjpa.global.exception.custom.InvalidCredentialsException;
import com.example.springjpa.global.jwt.TokenProvider;
import com.example.springjpa.schedule.api.dto.ScheduleUpdateRequest;
import com.example.springjpa.schedule.api.dto.request.AssignUsersRequest;
import com.example.springjpa.schedule.api.dto.request.ScheduleSaveRequest;
import com.example.springjpa.schedule.api.dto.response.ScheduleResponse;
import com.example.springjpa.schedule.application.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final ScheduleService scheduleService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@CookieValue(value = AUTHORIZATION_HEADER) String tokenValue,
                                                           @RequestBody @Valid ScheduleSaveRequest request) {
        Long userId = tokenProvider.getUserIdFromToken(tokenValue);
        ScheduleResponse response = scheduleService.create(userId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findAllSchedule(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());
        List<ScheduleResponse> responses = scheduleService.findAllByPage(pageable);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> findOneSchedule(@PathVariable("scheduleId") Long id) {
        ScheduleResponse response = scheduleService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<String> updateSchedule(@PathVariable("scheduleId") Long id,
                                                 @CookieValue(value = AUTHORIZATION_HEADER) String tokenValue,
                                                 @RequestBody @Valid ScheduleUpdateRequest request) {
        if (!tokenProvider.isUserAuthorizedWithToken(tokenValue)) {
            throw new InvalidCredentialsException("해당 유저는 권한이 없습니다.");
        }
        scheduleService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/assign/{scheduleId}")
    public ResponseEntity<String> assignUsersToSchedule(@PathVariable("scheduleId") Long id,
                                                        @CookieValue(value = AUTHORIZATION_HEADER) String tokenValue,
                                                        @RequestBody AssignUsersRequest request) {
        if (!tokenProvider.isUserAuthorizedWithToken(tokenValue)) {
            throw new InvalidCredentialsException("해당 유저는 권한이 없습니다.");
        }
        Long userId = tokenProvider.getUserIdFromToken(tokenValue);
        scheduleService.assignUsersToSchedule(id, userId, request.userIds());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("scheduleId") Long id,
                                                 @CookieValue(value = AUTHORIZATION_HEADER) String tokenValue) {
        if (!tokenProvider.isUserAuthorizedWithToken(tokenValue)) {
            throw new InvalidCredentialsException("해당 유저는 권한이 없습니다.");
        }
        scheduleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
