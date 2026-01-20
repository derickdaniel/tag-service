package com.microservice.tags.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.tags.entity.TagAssignmentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagAssignmentRepository extends JpaRepository<TagAssignmentEntity, Long> {

    // Find all assignments for a given entity (issue, project, etc.)
    List<TagAssignmentEntity> findByEntityTypeAndEntityId(String entityType, String entityId);

    // Find all assignments for a given tag
    List<TagAssignmentEntity> findByTag_Id(Long tagId);

    // Check if a tag is already assigned to an entity
    Optional<TagAssignmentEntity> findByTag_IdAndEntityTypeAndEntityId(Long tagId, String entityType, String entityId);

    // Delete all assignments for a given entity
    void deleteByEntityTypeAndEntityId(String entityType, String entityId);
}