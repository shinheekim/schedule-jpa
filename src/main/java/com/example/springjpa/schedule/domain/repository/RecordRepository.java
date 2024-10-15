package com.example.springjpa.schedule.domain.repository;

import com.example.springjpa.schedule.domain.Records;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Records, Long> {
}
