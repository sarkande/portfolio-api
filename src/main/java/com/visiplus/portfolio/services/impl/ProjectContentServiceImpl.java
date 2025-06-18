package com.visiplus.portfolio.services.impl;

import com.visiplus.portfolio.models.Project;
import com.visiplus.portfolio.models.ProjectContent;
import com.visiplus.portfolio.repository.ProjectContentRepository;
import com.visiplus.portfolio.services.ProjectContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectContentServiceImpl implements ProjectContentService {

    private final ProjectContentRepository projectContentRepository;

    @Override
    public ProjectContent create(Project project) {
        ProjectContent content = ProjectContent.builder()
                .project(project)
                .build();
        return projectContentRepository.save(content);
    }
}
