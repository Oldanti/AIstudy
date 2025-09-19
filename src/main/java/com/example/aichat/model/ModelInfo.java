package com.example.aichat.model;

public class ModelInfo {
    private String id;
    private String name;
    private String description;
    
    public ModelInfo() {}
    
    public ModelInfo(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
