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

    /**
     * プロジェクト名でプロジェクトを検索します（部分一致）。
     * @param projectName プロジェクト名
     * @param pageable ページング情報
     * @return 検索結果のプロジェクトのページ
     */
    Page<Project> findByProjectNameContaining(String projectName, Pageable pageable);

    /**
     * 部署IDでプロジェクトを検索します。
     * @param departmentId 部署ID
     * @param pageable ページング情報
     * @return 検索結果のプロジェクトのページ
     */
    Page<Project> findByDepartmentId(Long departmentId, Pageable pageable);

    /**
     * プロジェクトランクでプロジェクトを検索します。
     * @param rank プロジェクトランク
     * @param pageable ページング情報
     * @return 検索結果のプロジェクトのページ
     */
    Page<Project> findByProjectRank(ProjectRank rank, Pageable pageable);

    /**
     * 開始日でプロジェクトを検索します。
     * @param startDate 開始日
     * @param endDate 終了日
     * @param pageable ページング情報
     * @return 検索結果のプロジェクトのページ
     */
    Page<Project> findByStartDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * PM名でプロジェクトを検索します（部分一致）。
     * @param projectManagerName PM名
     * @param pageable ページング情報
     * @return 検索結果のプロジェクトのページ
     */
    Page<Project> findByProjectManagerNameContaining(String projectManagerName, Pageable pageable);

    /**
     * PL名でプロジェクトを検索します（部分一致）。
     * @param projectLeaderName PL名
     * @param pageable ページング情報
     * @return 検索結果のプロジェクトのページ
     */
    Page<Project> findByProjectLeaderNameContaining(String projectLeaderName, Pageable pageable);
}
