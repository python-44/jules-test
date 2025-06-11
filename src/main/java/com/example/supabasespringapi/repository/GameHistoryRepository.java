package com.example.supabasespringapi.repository;

import com.example.supabasespringapi.model.GameHistory;
import com.example.supabasespringapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    List<GameHistory> findByUserOrderByPlayedAtDesc(User user);
    List<GameHistory> findByUserIdOrderByScoreDesc(Long userId); // Example of finding by user ID
}
