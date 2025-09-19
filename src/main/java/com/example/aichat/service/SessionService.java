package com.example.aichat.service;

import com.example.aichat.model.User;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class SessionService {
    
    private static final String USER_SESSION_KEY = "current_user";
    
    public void setCurrentUser(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
    }
    
    public Optional<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute(USER_SESSION_KEY);
        return Optional.ofNullable(user);
    }
    
    public void clearCurrentUser(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }
    
    public boolean isLoggedIn(HttpSession session) {
        return getCurrentUser(session).isPresent();
    }
}
