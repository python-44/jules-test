package com.example.supabasespringapi.dto;

import com.example.supabasespringapi.model.User;
import java.sql.Timestamp;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Timestamp createdAt;
    private Integer highestScore;

    public UserResponse(Long id, String username, String email, Timestamp createdAt, Integer highestScore) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.highestScore = highestScore;
    }

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getHighestScore());
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Integer getHighestScore() { return highestScore; }
    public void setHighestScore(Integer highestScore) { this.highestScore = highestScore; }
}
