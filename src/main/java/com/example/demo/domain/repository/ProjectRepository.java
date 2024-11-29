package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Project;
import com.example.demo.domain.entity.Project.ProjectRank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    
    // 基本的なCRUD操作はJpaRepositoryから継承

    // プロジェクト名での検索（部分一致）
    Page<Project> findByProjectNameContaining(String projectName, Pageable pageable);

    // 部署IDでの検索
    Page<Project> findByDepartmentId(Long departmentId, Pageable pageable);

    // プロジェクトランクでの検索
    Page<Project> findByProjectRank(ProjectRank rank, Pageable pageable);

    // 期間での検索（開始日）
    Page<Project> findByStartDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // PM名での検索（部分一致）
    Page<Project> findByProjectManagerNameContaining(String projectManagerName, Pageable pageable);

    // PL名での検索（部分一致）
    Page<Project> findByProjectLeaderNameContaining(String projectLeaderName, Pageable pageable);
}
