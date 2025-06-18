package com.visiplus.portfolio.services;

import com.visiplus.portfolio.models.Project;

public interface ProjectService {
    Project create(Project project);

    Project update(String id, Project project);
}