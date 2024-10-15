package com.example.springjpa.user.domain.repository;

import com.example.springjpa.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
