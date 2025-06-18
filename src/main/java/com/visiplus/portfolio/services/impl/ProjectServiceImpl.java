package com.visiplus.portfolio.services.impl;

import com.visiplus.portfolio.models.Project;
import com.visiplus.portfolio.repository.ProjectRepository;
import com.visiplus.portfolio.repository.ProjectContentRepository;
import com.visiplus.portfolio.models.ProjectContent;
import com.visiplus.portfolio.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.text.Normalizer;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectContentRepository contentRepository;

    @Override
    public Project create(Project project) {
        String uniqueSlug = generateUniqueSlug(project.getTitle());
        project.setSlug(uniqueSlug);
        Project savedProject = projectRepository.save(project);

        String contentJson = "{}";
        if (project.getContent() != null && project.getContent().getContentJson() != null) {
            contentJson = project.getContent().getContentJson();
        }

        ProjectContent content = ProjectContent.builder()
                .project(savedProject)
                .contentJson(contentJson)
                .build();
        contentRepository.save(content);
        savedProject.setContent(content);

        return savedProject;
    }

    @Override
    public Project update(String id, Project updated) {
        return projectRepository.findById(id).map(existing -> {
            updated.setId(existing.getId());
            if (updated.getSlug() == null || updated.getSlug().isEmpty() || !updated.getSlug().equals(existing.getSlug())) {
                updated.setSlug(generateUniqueSlug(updated.getTitle()));
            }
            Project saved = projectRepository.save(updated);

            String json = "{}";
            if (updated.getContent() != null && updated.getContent().getContentJson() != null) {
                json = updated.getContent().getContentJson();
            }

            ProjectContent content = existing.getContent();
            if (content == null) {
                content = new ProjectContent();
                content.setProject(saved);
            }
            content.setContentJson(json);
            contentRepository.save(content);
            saved.setContent(content);

            return saved;
        }).orElse(null);
    }

    private String generateUniqueSlug(String title) {
        // Slug de base
        String baseSlug = toSlug(title);
        String slug = baseSlug;
        int counter = 1;

        // Boucle tant que le slug existe
        while (projectRepository.findBySlug(slug).isPresent()) {
            slug = baseSlug + "-" + counter;
            counter++;
        }

        return slug;
    }

    private String toSlug(String input) {
        // Supprimer les accents
        String nowhitespace = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        // Minuscule + remplacer espaces/ponctuation par tirets + supprimer tout le reste
        String slug = nowhitespace.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        return slug;
    }

}
