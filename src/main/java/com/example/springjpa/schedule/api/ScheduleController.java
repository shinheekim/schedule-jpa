package com.example.springjpa.schedule.api;

import com.example.springjpa.schedule.api.dto.ScheduleUpdateRequest;
import com.example.springjpa.schedule.api.dto.request.AssignUsersRequest;
import com.example.springjpa.schedule.api.dto.request.ScheduleSaveRequest;
import com.example.springjpa.schedule.api.dto.response.ScheduleResponse;
import com.example.springjpa.schedule.application.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody @Valid ScheduleSaveRequest request) {
        // 추후 토큰받아서 작성자 정보 넘기기 - @AuthenticationPrincipal UserDetail userDetail
        ScheduleResponse response = scheduleService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ScheduleResponse>> findAllSchedule(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());
        Page<ScheduleResponse> responses = scheduleService.findAllSchedules(pageable);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> findOneSchedule(@PathVariable("scheduleId") Long id) {
        ScheduleResponse response = scheduleService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<String> updateSchedule(@PathVariable("scheduleId") Long id,
                                                 @RequestBody @Valid ScheduleUpdateRequest request) {
        scheduleService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/assign/{scheduleId}")
    public ResponseEntity<String> assignUsersToSchedule(@PathVariable("scheduleId") Long id,
                                                        @RequestBody AssignUsersRequest request) {
        // 유저 id도 입력받기.
        scheduleService.assignUsersToSchedule(id, request.userIds());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("scheduleId") Long id) {
        scheduleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
