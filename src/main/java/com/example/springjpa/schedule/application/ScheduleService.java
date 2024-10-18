package com.example.springjpa.schedule.application;

import com.example.springjpa.global.exception.custom.InvalidCredentialsException;
import com.example.springjpa.schedule.api.dto.ScheduleUpdateRequest;
import com.example.springjpa.schedule.api.dto.request.ScheduleSaveRequest;
import com.example.springjpa.schedule.api.dto.response.ScheduleResponse;
import com.example.springjpa.schedule.domain.Schedule;
import com.example.springjpa.schedule.domain.UserSchedule;
import com.example.springjpa.schedule.domain.repository.ScheduleRepository;
import com.example.springjpa.schedule.domain.repository.UserScheduleRepository;
import com.example.springjpa.schedule.exception.ScheduleNotFoundException;
import com.example.springjpa.user.domain.User;
import com.example.springjpa.user.domain.repository.UserRepository;
import com.example.springjpa.user.exception.UserNotFoundException;
import com.example.springjpa.weather.application.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final UserScheduleRepository userScheduleRepository;
    private final WeatherService weatherService;

    public ScheduleResponse create(Long id, ScheduleSaveRequest request) {
        User creator = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Schedule schedule = Schedule.builder()
                .title(request.title())
                .content(request.content())
                .creator(creator)
                .build();

        String weather = weatherService.getWeatherForCreatedAt(schedule.getCreatedAt());
        schedule.setWeather(weather);

        scheduleRepository.save(schedule);

        UserSchedule userSchedule = new UserSchedule(schedule, creator);
        userScheduleRepository.save(userSchedule);

        return ScheduleResponse.from(schedule);
    }


    public List<ScheduleResponse> findAllByPage(Pageable pageable) {
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);

        return schedules.stream()
                .map(ScheduleResponse::from)
                .toList();
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

    /**
     * 일정을 작성한 유저는 추가로 일정 담당 유저들을 배치할 수 있습니다.
     */
    public void assignUsersToSchedule(Long id, Long userId, Set<Long> userIds) {
        Schedule schedule = findScheduleById(id);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        if (!schedule.isCreator(user)) {
            throw new InvalidCredentialsException("일정을 생성한 유저만 일정 관리 유저를 설정할 수 있습니다.");
        }

        Set<User> usersToAssign = new HashSet<>(userRepository.findAllById(userIds));

        for (User allocatedUser : usersToAssign) {
            UserSchedule userSchedule = new UserSchedule(schedule, allocatedUser);
            userScheduleRepository.save(userSchedule);
        }
    }

    public void delete(Long id) {
        Schedule schedule = findScheduleById(id);
        scheduleRepository.delete(schedule);
    }

    private Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));
    }
}
