package com.example.demo.feature.project_management.dto;

import com.example.demo.domain.entity.Project.ProjectRank;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class ProjectDTO {
    private Long id;
    
    @NotBlank(message = "プロジェクト名は必須です")
    private String projectName;
    
    @NotNull(message = "部署IDは必須です")
    private Long departmentId;
    
    @NotNull(message = "プロジェクトランクは必須です")
    private ProjectRank projectRank;
    
    @NotBlank(message = "PM氏名は必須です")
    private String projectManagerName;
    
    @NotBlank(message = "PL氏名は必須です")
    private String projectLeaderName;
    
    @NotNull(message = "開始日は必須です")
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private BigDecimal revenue;
    
    private String notes;
}
