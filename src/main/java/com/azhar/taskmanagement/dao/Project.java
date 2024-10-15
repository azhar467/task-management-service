package com.azhar.taskmanagement.dao;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "projects", schema = "task_management_service_schema")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @ToString.Exclude
    private User createdBy;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Task> tasks = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime updatedAt;
}