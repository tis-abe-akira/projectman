package com.example.demo.feature.project_management.service;

import com.example.demo.domain.entity.Project;
import com.example.demo.domain.entity.Project.ProjectRank;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class ProjectSpecifications {

    public static Specification<Project> withProjectName(String projectName) {
        return (root, query, cb) -> {
            if (projectName == null || projectName.isEmpty()) {
                return null;
            }
            return cb.like(root.get("projectName"), "%" + projectName + "%");
        };
    }

    public static Specification<Project> withDepartmentId(Long departmentId) {
        return (root, query, cb) -> {
            if (departmentId == null) {
                return null;
            }
            return cb.equal(root.get("departmentId"), departmentId);
        };
    }

    public static Specification<Project> withProjectRank(ProjectRank rank) {
        return (root, query, cb) -> {
            if (rank == null) {
                return null;
            }
            return cb.equal(root.get("projectRank"), rank);
        };
    }

    public static Specification<Project> withProjectManagerName(String pmName) {
        return (root, query, cb) -> {
            if (pmName == null || pmName.isEmpty()) {
                return null;
            }
            return cb.like(root.get("projectManagerName"), "%" + pmName + "%");
        };
    }

    public static Specification<Project> withProjectLeaderName(String plName) {
        return (root, query, cb) -> {
            if (plName == null || plName.isEmpty()) {
                return null;
            }
            return cb.like(root.get("projectLeaderName"), "%" + plName + "%");
        };
    }

    public static Specification<Project> withStartDateBetween(LocalDate startFrom, LocalDate startTo) {
        return (root, query, cb) -> {
            if (startFrom == null && startTo == null) {
                return null;
            }
            if (startFrom == null) {
                return cb.lessThanOrEqualTo(root.get("startDate"), startTo);
            }
            if (startTo == null) {
                return cb.greaterThanOrEqualTo(root.get("startDate"), startFrom);
            }
            return cb.between(root.get("startDate"), startFrom, startTo);
        };
    }

    public static Specification<Project> withEndDateBetween(LocalDate endFrom, LocalDate endTo) {
        return (root, query, cb) -> {
            if (endFrom == null && endTo == null) {
                return null;
            }
            if (endFrom == null) {
                return cb.lessThanOrEqualTo(root.get("endDate"), endTo);
            }
            if (endTo == null) {
                return cb.greaterThanOrEqualTo(root.get("endDate"), endFrom);
            }
            return cb.between(root.get("endDate"), endFrom, endTo);
        };
    }

    public static Specification<Project> withRevenueBetween(Long minRevenue, Long maxRevenue) {
        return (root, query, cb) -> {
            if (minRevenue == null && maxRevenue == null) {
                return null;
            }
            if (minRevenue == null) {
                return cb.lessThanOrEqualTo(root.get("revenue"), maxRevenue);
            }
            if (maxRevenue == null) {
                return cb.greaterThanOrEqualTo(root.get("revenue"), minRevenue);
            }
            return cb.between(root.get("revenue"), minRevenue, maxRevenue);
        };
    }
}
