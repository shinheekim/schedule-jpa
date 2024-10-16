package com.example.springjpa.schedule.application;

import com.example.springjpa.schedule.api.dto.ScheduleUpdateRequest;
import com.example.springjpa.schedule.api.dto.request.ScheduleSaveRequest;
import com.example.springjpa.schedule.api.dto.response.ScheduleResponse;
import com.example.springjpa.schedule.domain.Schedule;
import com.example.springjpa.schedule.domain.UserSchedule;
import com.example.springjpa.schedule.domain.repository.ScheduleRepository;
import com.example.springjpa.schedule.domain.repository.UserScheduleRepository;
import com.example.springjpa.user.domain.User;
import com.example.springjpa.user.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final UserScheduleRepository userScheduleRepository;

    public ScheduleResponse create(ScheduleSaveRequest request) {
        User creator = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Schedule schedule = Schedule.builder()
                .title(request.title())
                .content(request.content())
                .creator(creator)
                .build();
        scheduleRepository.save(schedule);

        UserSchedule userSchedule = new UserSchedule(schedule, creator);
        userScheduleRepository.save(userSchedule);

        return ScheduleResponse.from(schedule);
    }


    public Page<ScheduleResponse> findAllSchedules(Pageable pageable) {
        return scheduleRepository.findAll(pageable)
                .map(ScheduleResponse::from);
    }

    public ScheduleResponse findOne(Long id) {
        Schedule schedule = findScheduleById(id);
        return ScheduleResponse.from(schedule);
    }

    public void update(Long id, ScheduleUpdateRequest request) {
        Schedule schedule = findScheduleById(id);
        schedule.update(request.title(), request.content());
        scheduleRepository.save(schedule);
    }


    public void assignUsersToSchedule(Long scheduleId, Set<Long> userIds) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 일정을 찾을 수 없습니다."));

        Set<User> usersToAssign = new HashSet<>(userRepository.findAllById(userIds));

        for (User user : usersToAssign) {
            UserSchedule userSchedule = new UserSchedule(schedule, user);
            userScheduleRepository.save(userSchedule);
        }
    }

    public void delete(Long id) {
        Schedule schedule = findScheduleById(id);
        scheduleRepository.delete(schedule);
    }

    private Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 일정이 존재하지 않습니다."));
    }
}
