package com.visiplus.portfolio.services.impl;

import com.visiplus.portfolio.models.Project;
import com.visiplus.portfolio.repository.ProjectRepository;
import com.visiplus.portfolio.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project create(Project project) {
        // ignore si ID est fourni
        project.setId(null);

        String baseSlug = slugify(project.getTitle());
        String uniqueSlug = generateUniqueSlug(baseSlug, 0);
        project.setId(uniqueSlug);

        return projectRepository.save(project);
    }

    private String generateUniqueSlug(String base, int attempt) {
        String slug = base + (attempt > 0 ? "-" + attempt : "");
        return projectRepository.existsById(slug)
                ? generateUniqueSlug(base, attempt + 1)
                : slug;
    }

    private String slugify(String input) {
        return input.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .trim()
                .replaceAll("\\s+", "-");
    }
}
