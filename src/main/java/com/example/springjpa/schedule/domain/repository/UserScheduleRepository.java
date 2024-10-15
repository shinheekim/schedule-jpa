package com.example.springjpa.schedule.domain.repository;

import com.example.springjpa.schedule.domain.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {
}
