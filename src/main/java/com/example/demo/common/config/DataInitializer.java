package com.example.demo.common.config;

import com.example.demo.domain.entity.Project;
import com.example.demo.domain.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProjectRepository projectRepository) {
        return args -> {
            // プロジェクト1: 進行中の大規模プロジェクト
            Project project1 = new Project();
            project1.setProjectName("次世代クラウドプラットフォーム開発");
            project1.setDepartmentId(1L);
            project1.setProjectRank(Project.ProjectRank.SS);
            project1.setProjectManagerName("山田太郎");
            project1.setProjectLeaderName("鈴木一郎");
            project1.setStartDate(LocalDate.now().minusMonths(2));
            project1.setEndDate(LocalDate.now().plusMonths(10));
            project1.setRevenue(new BigDecimal("100000000"));
            project1.setNotes("戦略的重要プロジェクト。予定通り進行中。");

            // プロジェクト2: 小規模な保守プロジェクト
            Project project2 = new Project();
            project2.setProjectName("レガシーシステム保守");
            project2.setDepartmentId(2L);
            project2.setProjectRank(Project.ProjectRank.C);
            project2.setProjectManagerName("佐藤花子");
            project2.setProjectLeaderName("田中次郎");
            project2.setStartDate(LocalDate.now().minusMonths(1));
            project2.setRevenue(new BigDecimal("5000000"));
            project2.setNotes("定期的な保守作業と軽微な機能改善");

            projectRepository.save(project1);
            projectRepository.save(project2);
        };
    }
}
