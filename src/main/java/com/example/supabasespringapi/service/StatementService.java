package com.example.supabasespringapi.service;

import com.example.supabasespringapi.model.Statement;
import java.util.List;
import java.util.Optional;

public interface StatementService {
    Statement saveStatement(Statement statement);
    Optional<Statement> getStatementById(Long id);
    List<Statement> getAllStatements();
    List<Statement> getStatementsByCategory(String category);
    List<Statement> getStatementsByDifficulty(Integer difficulty);
    List<Statement> getStatementsByCategoryAndDifficulty(String category, Integer difficulty);
    void deleteStatement(Long id);
}
