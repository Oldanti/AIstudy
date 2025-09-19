package com.example.aichat.repository;

import com.example.aichat.model.User;
import com.example.aichat.model.UserAiConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAiConfigRepository extends JpaRepository<UserAiConfig, Long> {
    
    Optional<UserAiConfig> findByUser(User user);
    
    Optional<UserAiConfig> findByUserId(Long userId);
}
