package com.example.demo.feature.project_management.service;

import com.example.demo.domain.entity.Project;
import com.example.demo.domain.repository.ProjectRepository;
import com.example.demo.feature.project_management.dto.ProjectDTO;
import com.example.demo.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = convertToEntity(projectDTO);
        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    @Transactional(readOnly = true)
    public Optional<ProjectDTO> getProject(Long id) {
        return projectRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Page<ProjectDTO> searchProjects(Specification<Project> spec, Pageable pageable) {
        return projectRepository.findAll(spec, pageable)
                .map(this::convertToDTO);
    }

    @Transactional
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                    Project updatedProject = convertToEntity(projectDTO);
                    updatedProject.setId(id);
                    return convertToDTO(projectRepository.save(updatedProject));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
        projectRepository.delete(project);
    }

    private Project convertToEntity(ProjectDTO dto) {
        Project entity = new Project();
        entity.setId(dto.getId());
        entity.setProjectName(dto.getProjectName());
        entity.setDepartmentId(dto.getDepartmentId());
        entity.setProjectRank(dto.getProjectRank());
        entity.setProjectManagerName(dto.getProjectManagerName());
        entity.setProjectLeaderName(dto.getProjectLeaderName());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setRevenue(dto.getRevenue());
        entity.setNotes(dto.getNotes());
        return entity;
    }

    private ProjectDTO convertToDTO(Project entity) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(entity.getId());
        dto.setProjectName(entity.getProjectName());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setProjectRank(entity.getProjectRank());
        dto.setProjectManagerName(entity.getProjectManagerName());
        dto.setProjectLeaderName(entity.getProjectLeaderName());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setRevenue(entity.getRevenue());
        dto.setNotes(entity.getNotes());
        return dto;
    }
}
