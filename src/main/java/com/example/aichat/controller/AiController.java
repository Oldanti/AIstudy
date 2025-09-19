package com.example.aichat.controller;

import com.example.aichat.model.AiConfig;
import com.example.aichat.model.ModelInfo;
import com.example.aichat.model.User;
import com.example.aichat.model.UserAiConfig;
import com.example.aichat.service.AiService;
import com.example.aichat.service.SessionService;
import com.example.aichat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AiController {
    
    @Autowired
    private AiService aiService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SessionService sessionService;
    
    @PostMapping("/models")
    public ResponseEntity<List<ModelInfo>> getModels(@RequestBody AiConfig config) {
        try {
            List<ModelInfo> models = aiService.getModels(config.getUrl(), config.getApiKey());
            return ResponseEntity.ok(models);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/config")
    public ResponseEntity<String> saveConfig(@RequestBody AiConfig config, HttpSession session) {
        try {
            Optional<User> userOpt = sessionService.getCurrentUser(session);
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("请先登录");
            }
            
            User user = userOpt.get();
            userService.saveAiConfig(user.getId(), config.getUrl(), config.getApiKey(), config.getSelectedModel());
            return ResponseEntity.ok("配置保存成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("配置保存失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/config")
    public ResponseEntity<AiConfig> getConfig(HttpSession session) {
        try {
            Optional<User> userOpt = sessionService.getCurrentUser(session);
            if (userOpt.isEmpty()) {
                return ResponseEntity.ok(new AiConfig());
            }
            
            User user = userOpt.get();
            UserAiConfig userConfig = userService.getAiConfig(user.getId());
            
            if (userConfig != null) {
                AiConfig config = new AiConfig();
                config.setUrl(userConfig.getAiUrl());
                config.setApiKey(userConfig.getApiKey());
                config.setSelectedModel(userConfig.getSelectedModel());
                return ResponseEntity.ok(config);
            } else {
                return ResponseEntity.ok(new AiConfig());
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new AiConfig());
        }
    }
    
    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String message, HttpSession session) {
        try {
            Optional<User> userOpt = sessionService.getCurrentUser(session);
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("请先登录");
            }
            
            User user = userOpt.get();
            UserAiConfig userConfig = userService.getAiConfig(user.getId());
            
            if (userConfig == null || userConfig.getAiUrl() == null || 
                userConfig.getApiKey() == null || userConfig.getSelectedModel() == null) {
                return ResponseEntity.badRequest().body("请先配置AI服务");
            }
            
            AiConfig config = new AiConfig();
            config.setUrl(userConfig.getAiUrl());
            config.setApiKey(userConfig.getApiKey());
            config.setSelectedModel(userConfig.getSelectedModel());
            
            String response = aiService.chat(config, message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("聊天失败: " + e.getMessage());
        }
    }
}
