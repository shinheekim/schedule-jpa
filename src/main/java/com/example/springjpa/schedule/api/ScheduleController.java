package com.example.springjpa.schedule.api;

import com.example.springjpa.schedule.api.dto.ScheduleUpdateRequest;
import com.example.springjpa.schedule.api.dto.response.ScheduleResponse;
import com.example.springjpa.schedule.api.dto.request.ScheduleSaveRequest;
import com.example.springjpa.schedule.application.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody @Valid ScheduleSaveRequest request) {  // 추후 토큰받아서 작성자 정보 넘기기
        ScheduleResponse response = scheduleService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
}
