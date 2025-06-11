package com.example.supabasespringapi.controller;

import com.example.supabasespringapi.dto.UserResponse;
import com.example.supabasespringapi.model.User;
import com.example.supabasespringapi.service.UserService;
// Import for @PreAuthorize if using method-level security
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get current authenticated user's details
    @GetMapping("/me")
    // @PreAuthorize("isAuthenticated()") // Example of securing endpoint
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
        User user = userService.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }

    // Get user by ID (example, could be admin restricted)
    @GetMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')") // Example
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.findById(id) // Using the new findById method
            .orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }
}
