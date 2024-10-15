package com.example.springjpa.schedule.api;

import com.example.springjpa.schedule.api.dto.ScheduleResponse;
import com.example.springjpa.schedule.api.dto.ScheduleSaveRequest;
import com.example.springjpa.schedule.application.ScheduleService;
import com.example.springjpa.user.domain.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ResponseEntity<?> createSchedule(@RequestBody @Valid ScheduleSaveRequest request) {  // 추후 토큰받아서 작성자 정보 넘기기
        ScheduleResponse response = scheduleService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
