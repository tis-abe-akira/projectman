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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "プロジェクト管理", description = "プロジェクトの作成、取得、更新、削除を行うAPI")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "プロジェクトの新規作成", 
              description = "新しいプロジェクトを作成します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", 
                    description = "プロジェクトが正常に作成されました",
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
        @ApiResponse(responseCode = "400", 
                    description = "入力値が不正です",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(
            @Parameter(description = "作成するプロジェクトの情報", required = true)
            @Valid @RequestBody ProjectDTO projectDTO) {
        ProjectDTO created = projectService.createProject(projectDTO);
        return ResponseEntity
                .created(URI.create("/api/projects/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "プロジェクトの取得", 
              description = "指定されたIDのプロジェクトを取得します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "プロジェクトが正常に取得されました",
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
        @ApiResponse(responseCode = "404", 
                    description = "指定されたIDのプロジェクトが見つかりません",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProject(
            @Parameter(description = "プロジェクトID", required = true)
            @PathVariable Long id) {
        return projectService.getProject(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
    }

    @Operation(summary = "プロジェクトの検索", 
              description = "条件に一致するプロジェクトを検索します。いずれかの条件に一致するプロジェクトが返されます。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "検索結果が正常に取得されました",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> searchProjects(
            @Parameter(description = "検索条件")
            ProjectSearchRequest searchRequest,
            @Parameter(description = "ページング情報")
            @PageableDefault(size = 20) Pageable pageable) {
        
        Specification<Project> spec = null;

        // 各条件をORで結合
        if (searchRequest.getProjectName() != null) {
            spec = (spec == null) ? 
                ProjectSpecifications.withProjectName(searchRequest.getProjectName()) :
                spec.or(ProjectSpecifications.withProjectName(searchRequest.getProjectName()));
        }
        if (searchRequest.getDepartmentId() != null) {
            spec = (spec == null) ? 
                ProjectSpecifications.withDepartmentId(searchRequest.getDepartmentId()) :
                spec.or(ProjectSpecifications.withDepartmentId(searchRequest.getDepartmentId()));
        }
        if (searchRequest.getProjectRank() != null) {
            spec = (spec == null) ? 
                ProjectSpecifications.withProjectRank(searchRequest.getProjectRank()) :
                spec.or(ProjectSpecifications.withProjectRank(searchRequest.getProjectRank()));
        }
        if (searchRequest.getProjectManagerName() != null) {
            spec = (spec == null) ? 
                ProjectSpecifications.withProjectManagerName(searchRequest.getProjectManagerName()) :
                spec.or(ProjectSpecifications.withProjectManagerName(searchRequest.getProjectManagerName()));
        }
        if (searchRequest.getProjectLeaderName() != null) {
            spec = (spec == null) ? 
                ProjectSpecifications.withProjectLeaderName(searchRequest.getProjectLeaderName()) :
                spec.or(ProjectSpecifications.withProjectLeaderName(searchRequest.getProjectLeaderName()));
        }
        
        if (searchRequest.getStartDateFrom() != null || searchRequest.getStartDateTo() != null) {
            Specification<Project> dateSpec = ProjectSpecifications.withStartDateBetween(
                searchRequest.getStartDateFrom(), 
                searchRequest.getStartDateTo()
            );
            spec = (spec == null) ? dateSpec : spec.or(dateSpec);
        }
        
        if (searchRequest.getEndDateFrom() != null || searchRequest.getEndDateTo() != null) {
            Specification<Project> dateSpec = ProjectSpecifications.withEndDateBetween(
                searchRequest.getEndDateFrom(), 
                searchRequest.getEndDateTo()
            );
            spec = (spec == null) ? dateSpec : spec.or(dateSpec);
        }
        
        if (searchRequest.getMinRevenue() != null || searchRequest.getMaxRevenue() != null) {
            Specification<Project> revenueSpec = ProjectSpecifications.withRevenueBetween(
                searchRequest.getMinRevenue(), 
                searchRequest.getMaxRevenue()
            );
            spec = (spec == null) ? revenueSpec : spec.or(revenueSpec);
        }

        // 検索条件が1つも指定されていない場合は全件取得
        if (spec == null) {
            spec = Specification.where(null);
        }

        Page<ProjectDTO> projects = projectService.searchProjects(spec, pageable);
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "プロジェクトの更新", 
              description = "指定されたIDのプロジェクト情報を更新します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "プロジェクトが正常に更新されました",
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
        @ApiResponse(responseCode = "400", 
                    description = "入力値が不正です",
                    content = @Content),
        @ApiResponse(responseCode = "404", 
                    description = "指定されたIDのプロジェクトが見つかりません",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(
            @Parameter(description = "プロジェクトID", required = true)
            @PathVariable Long id,
            @Parameter(description = "更新するプロジェクトの情報", required = true)
            @Valid @RequestBody ProjectDTO projectDTO) {
        ProjectDTO updated = projectService.updateProject(id, projectDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "プロジェクトの削除", 
              description = "指定されたIDのプロジェクトを削除します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", 
                    description = "プロジェクトが正常に削除されました"),
        @ApiResponse(responseCode = "404", 
                    description = "指定されたIDのプロジェクトが見つかりません",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @Parameter(description = "プロジェクトID", required = true)
            @PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
