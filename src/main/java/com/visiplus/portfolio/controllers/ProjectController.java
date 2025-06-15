package com.visiplus.portfolio.controllers;

import com.visiplus.portfolio.models.Project;
import com.visiplus.portfolio.repository.ProjectRepository;
import com.visiplus.portfolio.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    private final ProjectRepository projectRepository;

    @GetMapping
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable String id) {
        return projectRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Project create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable String id, @RequestBody Project updated) {
        return projectRepository.findById(id).map(project -> {
            updated.setId(project.getId());
            return ResponseEntity.ok(projectRepository.save(updated));
        }).orElse(ResponseEntity.notFound().build());
    }
}
