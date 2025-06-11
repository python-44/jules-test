package com.example.supabasespringapi.service;

import com.example.supabasespringapi.model.GameHistory;
import com.example.supabasespringapi.model.User;
import com.example.supabasespringapi.repository.GameHistoryRepository;
import com.example.supabasespringapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class GameHistoryServiceImpl implements GameHistoryService {

    private final GameHistoryRepository gameHistoryRepository;
    private final UserRepository userRepository; // To update highest_score

    @Autowired
    public GameHistoryServiceImpl(GameHistoryRepository gameHistoryRepository, UserRepository userRepository) {
        this.gameHistoryRepository = gameHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public GameHistory recordGame(User user, int score) {
        GameHistory gameHistory = new GameHistory();
        gameHistory.setUser(user);
        gameHistory.setScore(score);
        // playedAt is set by database default

        // Update user's highest score if this game's score is higher
        if (score > user.getHighestScore()) {
            user.setHighestScore(score);
            userRepository.save(user); // Save the updated user
        }
        return gameHistoryRepository.save(gameHistory);
    }

    @Override
    public List<GameHistory> getGameHistoryForUser(User user) {
        return gameHistoryRepository.findByUserOrderByPlayedAtDesc(user);
    }

    @Override
    public List<GameHistory> getLeaderboardByScoreForUser(Long userId) {
        // This method name in repository might be better as findTopNByUserIdOrderByScoreDesc if we want a leaderboard
        // For now, it fetches all and could be limited/paginated in the future
        return gameHistoryRepository.findByUserIdOrderByScoreDesc(userId);
    }
}
