package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * プロジェクトエンティティ
 */
@Entity
@Table(name = "projects")
@Data
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private Long departmentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectRank projectRank;

    @Column(nullable = false)
    private String projectManagerName;

    @Column(nullable = false)
    private String projectLeaderName;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal revenue;

    private String notes;

    /**
     * プロジェクトランクの列挙型
     */
    public enum ProjectRank {
        SS, A, B, C, D
    }
}
