# Gemini Project Analysis: AI Chat

## 1. Project Overview

This project is a versatile and configurable multi-model AI chat web application built with Spring Boot. Its core value lies in providing a user-friendly interface for interacting with external AI services. Instead of being tied to a specific AI provider, it allows each user to securely configure their own AI service endpoint (URL), API Key, and preferred model. This makes it a flexible bridge between the user and any AI service that is compatible with the OpenAI API format.

## 2. Core Features

- **User Authentication**: Standard user management system including registration, login, and logout functionalities. User sessions are managed to maintain login state.
- **Personalized AI Configuration**: Each user can set up and save their own AI service configuration, which includes:
    - The base URL of the AI service.
    - The API Key for authentication.
- **Dynamic Model Discovery**: The application can dynamically fetch a list of available AI models from the user-configured URL.
- **Model Selection**: Users can select a specific model from the discovered list to use for their chat sessions.
- **Real-time AI Chat**: Once configured, users can engage in conversations with the selected AI model through a simple chat interface.

## 3. Technology Stack

- **Backend**:
    - **Framework**: Spring Boot 3
    - **Language**: Java 17
    - **Web**: Spring Web, Spring WebFlux (for reactive HTTP client)
    - **Database**: Spring Data JPA, MySQL
    - **Security**: Spring Security Crypto (for password hashing)
    - **Session Management**: Spring Session (potentially with Redis for scalability)
- **Frontend**:
    - **Templating Engine**: Thymeleaf
- **Build Tool**:
    - **Tool**: Apache Maven

## 4. Project Structure

The project follows a standard layered architecture, separating concerns for better maintainability.

```
src/main/java/com/example/aichat/
├── controller/       # (Presentation Layer) Handles incoming HTTP requests and API endpoints.
│   ├── AiController.java       # Manages AI configuration and chat interactions.
│   ├── AuthController.java     # Manages user registration, login, and session.
│   └── PageController.java     # Serves the main application page.
│
├── service/          # (Service Layer) Contains the core business logic.
│   ├── AiService.java          # Logic for interacting with the external AI service.
│   ├── SessionService.java     # Helper service for managing user session data.
│   └── UserService.java        # Logic for user management and AI configuration storage.
│
├── repository/       # (Data Access Layer) Interfaces for database operations.
│   ├── UserRepository.java     # JPA repository for the 'users' table.
│   └── UserAiConfigRepository.java # JPA repository for the 'user_ai_configs' table.
│
├── model/            # (Domain Layer) Defines the data structures and entities.
│   ├── User.java               # Entity representing the 'users' table.
│   ├── UserAiConfig.java       # Entity representing the 'user_ai_configs' table.
│   ├── AiConfig.java           # DTO for transferring AI configuration data.
│   ├── ChatMessage.java        # DTO for chat messages.
│   └── ModelInfo.java          # DTO for representing an available AI model.
│
└── AichatApplication.java # Main entry point for the Spring Boot application.
```

## 5. API Endpoints

All API endpoints are located under the `/api` prefix.

### Authentication (`/api/auth`)

- `POST /register`: Registers a new user.
- `POST /login`: Authenticates a user and creates a session.
- `POST /logout`: Logs the user out and invalidates the session.
- `GET /current-user`: Retrieves information about the currently logged-in user.

### AI Interaction (`/api`)

- `POST /models`: Fetches the list of available AI models from a given URL and API Key.
- `GET /config`: Retrieves the current user's saved AI configuration.
- `POST /config`: Saves or updates the current user's AI configuration.
- `POST /chat`: Sends a message to the configured AI service and returns the response.

## 6. Database Schema

The database consists of two main tables.

### `users` Table
Stores user account information.

| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `BIGINT` | Primary Key |
| `username` | `VARCHAR(50)` | Unique username |
| `password` | `VARCHAR(100)` | BCrypt-hashed password |
| `email` | `VARCHAR(100)` | Unique user email |
| `created_at` | `TIMESTAMP` | Timestamp of creation |
| `updated_at` | `TIMESTAMP` | Timestamp of last update |

### `user_ai_configs` Table
Stores the personalized AI configuration for each user.

| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `BIGINT` | Primary Key |
| `user_id` | `BIGINT` | Foreign Key referencing `users.id` |
| `ai_url` | `VARCHAR(500)` | The base URL of the external AI service |
| `api_key` | `VARCHAR(500)` | The API Key for the AI service |
| `selected_model`| `VARCHAR(100)` | The name of the model the user has selected |
| `created_at` | `TIMESTAMP` | Timestamp of creation |
| `updated_at` | `TIMESTAMP` | Timestamp of last update |
