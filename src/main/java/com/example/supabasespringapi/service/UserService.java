package com.example.supabasespringapi.service;

import com.example.supabasespringapi.model.User;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    // Add other user-related service methods if needed
}
