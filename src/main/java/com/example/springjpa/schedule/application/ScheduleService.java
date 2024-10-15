package com.example.springjpa.schedule.application;

import com.example.springjpa.schedule.api.dto.response.ScheduleResponse;
import com.example.springjpa.schedule.api.dto.request.ScheduleSaveRequest;
import com.example.springjpa.schedule.domain.Schedule;
import com.example.springjpa.schedule.domain.UserSchedule;
import com.example.springjpa.schedule.domain.repository.ScheduleRepository;
import com.example.springjpa.schedule.domain.repository.UserScheduleRepository;
import com.example.springjpa.user.domain.User;
import com.example.springjpa.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final UserScheduleRepository userScheduleRepository;

    public ScheduleResponse create(ScheduleSaveRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Schedule schedule = Schedule.builder()
                .title(request.title())
                .content(request.content())
                .build();
        scheduleRepository.save(schedule);

        UserSchedule userSchedule = new UserSchedule(schedule, user);
        userScheduleRepository.save(userSchedule);

        return ScheduleResponse.from(schedule);
    }
}
