package com.visiplus.portfolio.controllers;

import com.visiplus.portfolio.models.ProjectContent;
import com.visiplus.portfolio.repository.ProjectContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project-contents")
@RequiredArgsConstructor
public class ProjectContentController {

    private final ProjectContentRepository projectContentRepository;

    @PostMapping
    public ProjectContent save(@RequestBody ProjectContent content) {
        return projectContentRepository.save(content);
    }
}
