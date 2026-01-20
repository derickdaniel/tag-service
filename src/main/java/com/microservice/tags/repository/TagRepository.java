package com.microservice.tags.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.tags.entity.TagEntity;

import java.util.Optional;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByUuid(String uuid);

    Optional<TagEntity> findByName(String name);

    Optional<TagEntity> findBySlug(String slug);

    // Search tags by partial name (case-insensitive)
    List<TagEntity> findByNameContainingIgnoreCase(String keyword);

    // Check existence by name
    boolean existsByName(String name);

    // Check existence by slug
    boolean existsBySlug(String slug);
}
