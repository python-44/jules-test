package com.veras.mythOrFactLGBT.dto;

import com.veras.mythOrFactLGBT.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
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

    // Manual getters and setters are removed
}
