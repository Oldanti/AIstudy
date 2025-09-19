package com.example.aichat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_ai_configs")
public class UserAiConfig {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "ai_url", length = 500)
    private String aiUrl;
    
    @Column(name = "api_key", length = 500)
    private String apiKey;
    
    @Column(name = "selected_model", length = 100)
    private String selectedModel;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 构造函数
    public UserAiConfig() {}
    
    public UserAiConfig(User user, String aiUrl, String apiKey, String selectedModel) {
        this.user = user;
        this.aiUrl = aiUrl;
        this.apiKey = apiKey;
        this.selectedModel = selectedModel;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getAiUrl() {
        return aiUrl;
    }
    
    public void setAiUrl(String aiUrl) {
        this.aiUrl = aiUrl;
    }
    
    public String getApiKey() {
        return apiKey;
    }
    
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public String getSelectedModel() {
        return selectedModel;
    }
    
    public void setSelectedModel(String selectedModel) {
        this.selectedModel = selectedModel;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
