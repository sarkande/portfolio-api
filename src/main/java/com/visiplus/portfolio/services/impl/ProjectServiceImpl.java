package com.visiplus.portfolio.services.impl;

import com.visiplus.portfolio.exceptions.ProjectNotFoundException;
import com.visiplus.portfolio.models.Project;
import com.visiplus.portfolio.repository.ProjectRepository;
import com.visiplus.portfolio.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.text.Normalizer;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project create(Project project) {
        String uniqueSlug = generateUniqueSlug(project.getTitle());
        project.setSlug(uniqueSlug);
        return projectRepository.save(project);
    }

    @Override
    public Project update(String slug, Project updated) {
        // Vérifier que le projet existe et récupérer son id
        Project existing = projectRepository.findBySlug(slug)
                .orElseThrow(ProjectNotFoundException::new);
        updated.setId(existing.getId());

        // Regénérer le slug à partir du titre
        updated.setSlug(generateUniqueSlug(updated.getTitle()));

        // Sauvegarder tout le projet mis à jour
        return projectRepository.save(updated);
    }

    @Override
    public Project findBySlug(String slug) {
        return projectRepository.findBySlug(slug)
                .orElseThrow(ProjectNotFoundException::new);
    }

    @Override
    public void delete(String slug) {
        Project project = projectRepository.findBySlug(slug)
                .orElseThrow(ProjectNotFoundException::new);
        projectRepository.delete(project);
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
