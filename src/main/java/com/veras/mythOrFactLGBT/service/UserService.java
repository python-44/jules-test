package com.veras.mythOrFactLGBT.service;

import com.veras.mythOrFactLGBT.model.User;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<UserResponse> getGlobalLeaderboard();
    // Add other user-related service methods if needed
}
