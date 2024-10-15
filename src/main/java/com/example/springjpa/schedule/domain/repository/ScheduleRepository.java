package com.example.springjpa.schedule.domain.repository;

import com.example.springjpa.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
