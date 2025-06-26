package com.visiplus.portfolio.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String slug;

    private String title;
    private String description;

    private String startDate;
    private String endDate;

    @ElementCollection
    private List<String> technologies;

    @ElementCollection
    private List<String> tags;

    private String category;

    private String thumbnailUrl;

    @ElementCollection
    private List<String> gallery;

    private String gitUrl;
    private String liveUrl;

    private String role;

    private boolean isFeatured;
    private boolean active;

    @Lob
    private String content;
}
