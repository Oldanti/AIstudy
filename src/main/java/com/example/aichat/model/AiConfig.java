package com.example.aichat.model;

public class AiConfig {
    private String url;
    private String apiKey;
    private String selectedModel;
    
    public AiConfig() {}
    
    public AiConfig(String url, String apiKey, String selectedModel) {
        this.url = url;
        this.apiKey = apiKey;
        this.selectedModel = selectedModel;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
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
}
