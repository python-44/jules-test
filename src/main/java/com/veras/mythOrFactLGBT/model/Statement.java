package com.veras.mythOrFactLGBT.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "statements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    // Manual getters and setters are removed
}
