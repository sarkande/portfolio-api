package com.visiplus.portfolio.repository;

import com.visiplus.portfolio.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
}