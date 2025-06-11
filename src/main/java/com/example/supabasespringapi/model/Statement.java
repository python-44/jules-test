package com.example.supabasespringapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "statements")
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // For TEXT type
    @Column(nullable = false)
    private String statement;

    @Column(name = "is_fact", nullable = false)
    private boolean isFact;

    @Lob // For TEXT type
    @Column(nullable = false)
    private String explanation;

    @Column(nullable = false)
    private Integer difficulty; // 1: Easy, 2: Medium, 3: Hard, 4: Expert

    @Column(nullable = false, length = 50)
    private String category;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public boolean isFact() {
        return isFact;
    }

    public void setFact(boolean fact) {
        isFact = fact;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
