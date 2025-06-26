package com.visiplus.portfolio.controllers;

import com.visiplus.portfolio.models.Project;
import com.visiplus.portfolio.repository.ProjectRepository;
import com.visiplus.portfolio.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    @Autowired
    private  ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Project> findBySlug(@PathVariable String slug) {
        return projectRepository.findBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Project create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<Project> update(@PathVariable String slug, @RequestBody Project updated) {
        Project result = projectService.update(slug, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
