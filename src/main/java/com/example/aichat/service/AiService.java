package com.example.aichat.service;

import com.example.aichat.model.AiConfig;
import com.example.aichat.model.ChatMessage;
import com.example.aichat.model.ModelInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiService {
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    public AiService() {
        this.webClient = WebClient.builder().build();
        this.objectMapper = new ObjectMapper();
    }
    
    public List<ModelInfo> getModels(String url, String apiKey) {
        try {
            // 清理URL，确保格式正确
            String cleanUrl = url.trim();
            if (cleanUrl.endsWith("/")) {
                cleanUrl = cleanUrl.substring(0, cleanUrl.length() - 1);
            }
            
            // 如果URL已经包含/v1，就直接使用，否则添加/v1
            String modelsUrl;
            if (cleanUrl.endsWith("/v1")) {
                modelsUrl = cleanUrl + "/models";
            } else {
                modelsUrl = cleanUrl + "/v1/models";
            }
            
            System.out.println("正在请求模型列表URL: " + modelsUrl);
            
            String response = webClient.get()
                    .uri(modelsUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            
            System.out.println("API响应: " + response);
            
            JsonNode jsonNode = objectMapper.readTree(response);
            List<ModelInfo> models = new ArrayList<>();
            
            if (jsonNode.has("data")) {
                for (JsonNode modelNode : jsonNode.get("data")) {
                    String id = modelNode.get("id").asText();
                    String name = modelNode.has("name") ? modelNode.get("name").asText() : id;
                    String description = modelNode.has("description") ? modelNode.get("description").asText() : "";
                    System.out.println("找到模型: " + id + " - " + name);
                    models.add(new ModelInfo(id, name, description));
                }
            } else {
                System.out.println("响应中没有'data'字段，完整响应: " + response);
            }
            
            System.out.println("总共找到 " + models.size() + " 个模型");
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public String chat(AiConfig config, String message) {
        try {
            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage("user", message));
            
            String requestBody = objectMapper.writeValueAsString(new ChatRequest(
                    config.getSelectedModel(),
                    messages,
                    1000,
                    0.7
            ));
            
            // 清理URL，确保格式正确
            String cleanUrl = config.getUrl().trim();
            if (cleanUrl.endsWith("/")) {
                cleanUrl = cleanUrl.substring(0, cleanUrl.length() - 1);
            }
            
            // 如果URL已经包含/v1，就直接使用，否则添加/v1
            String chatUrl;
            if (cleanUrl.endsWith("/v1")) {
                chatUrl = cleanUrl + "/chat/completions";
            } else {
                chatUrl = cleanUrl + "/v1/chat/completions";
            }
            
            System.out.println("正在请求聊天URL: " + chatUrl);
            
            String response = webClient.post()
                    .uri(chatUrl)
                    .header("Authorization", "Bearer " + config.getApiKey())
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            
            JsonNode jsonNode = objectMapper.readTree(response);
            if (jsonNode.has("choices") && jsonNode.get("choices").size() > 0) {
                return jsonNode.get("choices").get(0).get("message").get("content").asText();
            }
            
            return "抱歉，AI回复解析失败";
        } catch (Exception e) {
            e.printStackTrace();
            return "抱歉，AI服务调用失败: " + e.getMessage();
        }
    }
    
    // 内部类用于构建请求
    public static class ChatRequest {
        public String model;
        public List<ChatMessage> messages;
        public int max_tokens;
        public double temperature;
        
        public ChatRequest(String model, List<ChatMessage> messages, int max_tokens, double temperature) {
            this.model = model;
            this.messages = messages;
            this.max_tokens = max_tokens;
            this.temperature = temperature;
        }
    }
}
