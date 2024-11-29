package com.example.demo.feature.project_management.controller;

import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.domain.entity.Project;
import com.example.demo.feature.project_management.dto.ProjectDTO;
import com.example.demo.feature.project_management.dto.ProjectSearchRequest;
import com.example.demo.feature.project_management.service.ProjectService;
import com.example.demo.feature.project_management.service.ProjectSpecifications;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        ProjectDTO created = projectService.createProject(projectDTO);
        return ResponseEntity
                .created(URI.create("/api/projects/" + created.getId()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id) {
        return projectService.getProject(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
    }

    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> searchProjects(
            ProjectSearchRequest searchRequest,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Specification<Project> spec = Specification.where(null);

        if (searchRequest.getProjectName() != null) {
            spec = spec.and(ProjectSpecifications.withProjectName(searchRequest.getProjectName()));
        }
        if (searchRequest.getDepartmentId() != null) {
            spec = spec.and(ProjectSpecifications.withDepartmentId(searchRequest.getDepartmentId()));
        }
        if (searchRequest.getProjectRank() != null) {
            spec = spec.and(ProjectSpecifications.withProjectRank(searchRequest.getProjectRank()));
        }
        if (searchRequest.getProjectManagerName() != null) {
            spec = spec.and(ProjectSpecifications.withProjectManagerName(searchRequest.getProjectManagerName()));
        }
        if (searchRequest.getProjectLeaderName() != null) {
            spec = spec.and(ProjectSpecifications.withProjectLeaderName(searchRequest.getProjectLeaderName()));
        }
        
        spec = spec.and(ProjectSpecifications.withStartDateBetween(
            searchRequest.getStartDateFrom(), 
            searchRequest.getStartDateTo()
        ));
        
        spec = spec.and(ProjectSpecifications.withEndDateBetween(
            searchRequest.getEndDateFrom(), 
            searchRequest.getEndDateTo()
        ));
        
        spec = spec.and(ProjectSpecifications.withRevenueBetween(
            searchRequest.getMinRevenue(), 
            searchRequest.getMaxRevenue()
        ));

        Page<ProjectDTO> projects = projectService.searchProjects(spec, pageable);
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDTO projectDTO) {
        ProjectDTO updated = projectService.updateProject(id, projectDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
