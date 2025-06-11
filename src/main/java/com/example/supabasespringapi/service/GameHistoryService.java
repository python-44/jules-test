package com.example.supabasespringapi.service;

import com.example.supabasespringapi.model.GameHistory;
import com.example.supabasespringapi.model.User;
import java.util.List;

public interface GameHistoryService {
    GameHistory recordGame(User user, int score);
    List<GameHistory> getGameHistoryForUser(User user);
    List<GameHistory> getLeaderboardByScoreForUser(Long userId);
}
