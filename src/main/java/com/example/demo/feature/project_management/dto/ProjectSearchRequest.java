package com.example.demo.feature.project_management.dto;

import com.example.demo.domain.entity.Project.ProjectRank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class ProjectSearchRequest {
    private String projectName;
    private Long departmentId;
    private ProjectRank projectRank;
    private String projectManagerName;
    private String projectLeaderName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDateFrom;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDateTo;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateFrom;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateTo;
    
    private Long minRevenue;
    private Long maxRevenue;
}
