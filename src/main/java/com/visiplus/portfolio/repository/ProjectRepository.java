package com.visiplus.portfolio.repository;

import com.visiplus.portfolio.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, String> {
    Optional<Project> findBySlug(String slug);
}