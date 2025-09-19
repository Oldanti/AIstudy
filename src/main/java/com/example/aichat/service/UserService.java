package com.example.aichat.service;

import com.example.aichat.model.User;
import com.example.aichat.model.UserAiConfig;
import com.example.aichat.repository.UserRepository;
import com.example.aichat.repository.UserAiConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserAiConfigRepository userAiConfigRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public User register(String username, String password, String email) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("邮箱已存在");
        }
        
        // 创建新用户
        User user = new User(username, passwordEncoder.encode(password), email);
        return userRepository.save(user);
    }
    
    public User login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        return user;
    }
    
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }
    
    public UserAiConfig saveAiConfig(Long userId, String aiUrl, String apiKey, String selectedModel) {
        User user = findById(userId);
        
        // 查找现有配置
        Optional<UserAiConfig> existingConfig = userAiConfigRepository.findByUser(user);
        
        UserAiConfig config;
        if (existingConfig.isPresent()) {
            // 更新现有配置
            config = existingConfig.get();
            config.setAiUrl(aiUrl);
            config.setApiKey(apiKey);
            config.setSelectedModel(selectedModel);
        } else {
            // 创建新配置
            config = new UserAiConfig(user, aiUrl, apiKey, selectedModel);
        }
        
        return userAiConfigRepository.save(config);
    }
    
    public UserAiConfig getAiConfig(Long userId) {
        User user = findById(userId);
        return userAiConfigRepository.findByUser(user)
                .orElse(null);
    }
}
