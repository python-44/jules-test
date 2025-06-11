package com.example.supabasespringapi.controller;

import com.example.supabasespringapi.model.GameHistory;
import com.example.supabasespringapi.model.User;
import com.example.supabasespringapi.service.GameHistoryService;
import com.example.supabasespringapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
// import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/gamehistory")
public class GameHistoryController {

    private final GameHistoryService gameHistoryService;
    private final UserService userService;

    @Autowired
    public GameHistoryController(GameHistoryService gameHistoryService, UserService userService) {
        this.gameHistoryService = gameHistoryService;
        this.userService = userService;
    }

    @PostMapping
    // @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> recordGameHistory(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String, Integer> payload) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        Integer score = payload.get("score");
        if (score == null) {
            return ResponseEntity.badRequest().body("Score is required");
        }

        User user = userService.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));

        GameHistory gameRecord = gameHistoryService.recordGame(user, score);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameRecord);
    }

    @GetMapping("/user/me")
    // @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCurrentUserGameHistory(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        User user = userService.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));
        List<GameHistory> history = gameHistoryService.getGameHistoryForUser(user);
        return ResponseEntity.ok(history);
    }

    // Example: Get game history for a specific user (could be admin or public profile)
    @GetMapping("/user/{userId}")
    // @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id") // Example security
    public ResponseEntity<?> getUserGameHistory(@PathVariable Long userId) {
        User user = userService.findById(userId) // Assumes findById is now in UserService
            .orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<GameHistory> history = gameHistoryService.getGameHistoryForUser(user);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/leaderboard/user/{userId}")
    public ResponseEntity<List<GameHistory>> getLeaderboardForUser(@PathVariable Long userId) {
        // This currently fetches all history for a user, ordered by score.
        // True leaderboard might be top N scores across all users, or top N for a specific user.
        // The current service method is findByUserIdOrderByScoreDesc.
        List<GameHistory> leaderboardEntries = gameHistoryService.getLeaderboardByScoreForUser(userId);
        if (leaderboardEntries.isEmpty()) {
             // Check if user exists to differentiate no history vs no user
            if(userService.findById(userId).isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(leaderboardEntries);
    }
}
