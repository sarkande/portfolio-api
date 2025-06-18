package com.visiplus.portfolio.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visiplus.portfolio.models.Project;
import com.visiplus.portfolio.models.User;
import com.visiplus.portfolio.repository.ProjectRepository;
import com.visiplus.portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.InputStream;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ProjectRepository projectRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        // --- 1) Création de l'utilisateur admin ---
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin user created: admin / admin");
        }

        // --- 2) Chargement des projets mock ---
        if (projectRepository.count() == 0) {
            ClassPathResource resource = new ClassPathResource("mockProjects.json");
            try (InputStream is = resource.getInputStream()) {
                List<Project> projects = objectMapper.readValue(is, new TypeReference<List<Project>>() {});

//                For each project, set the slug if it's not already set
                projects.forEach(project -> {
                    if (project.getSlug() == null || project.getSlug().isEmpty()) {
                        project.setSlug(project.getTitle().toLowerCase().replaceAll("[^a-z0-9]+", "-"));
                    }
                });
                projectRepository.saveAll(projects);
                System.out.println("✅ Loaded " + projects.size() + " projects into database.");
            }
        }
    }
}
