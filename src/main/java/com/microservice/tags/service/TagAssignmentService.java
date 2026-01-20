package com.microservice.tags.service;

import org.springframework.stereotype.Service;

import com.microservice.tags.entity.TagAssignmentEntity;
import com.microservice.tags.entity.TagEntity;
import com.microservice.tags.repository.TagAssignmentRepository;
import com.microservice.tags.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TagAssignmentService {

    private final TagAssignmentRepository tagAssignmentRepository;
    private final TagRepository tagRepository;

    public TagAssignmentService(TagAssignmentRepository tagAssignmentRepository,
                                TagRepository tagRepository) {
        this.tagAssignmentRepository = tagAssignmentRepository;
        this.tagRepository = tagRepository;
    }

    // Assign a tag to an entity
    public TagAssignmentEntity assignTag(Long tagId, String entityType, String entityId, Integer createdBy) {
        Optional<TagEntity> tagOpt = tagRepository.findById(tagId);
        if (tagOpt.isEmpty()) {
            throw new IllegalArgumentException("Tag not found with id: " + tagId);
        }

        // Check if already assigned
        Optional<TagAssignmentEntity> existing = tagAssignmentRepository
                .findByTag_IdAndEntityTypeAndEntityId(tagId, entityType, entityId);
        if (existing.isPresent()) {
            return existing.get(); // return existing assignment
        }

        TagAssignmentEntity assignment = new TagAssignmentEntity(tagOpt.get(), entityType, entityId, createdBy);
        return tagAssignmentRepository.save(assignment);
    }

    // Get all tags assigned to an entity
    public List<TagAssignmentEntity> getAssignmentsForEntity(String entityType, String entityId) {
        return tagAssignmentRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    // Get all entities assigned to a tag
    public List<TagAssignmentEntity> getAssignmentsForTag(Long tagId) {
        return tagAssignmentRepository.findByTag_Id(tagId);
    }

    // Remove a tag assignment
    public boolean removeAssignment(Long tagId, String entityType, String entityId) {
        Optional<TagAssignmentEntity> assignment = tagAssignmentRepository
                .findByTag_IdAndEntityTypeAndEntityId(tagId, entityType, entityId);
        if (assignment.isPresent()) {
            tagAssignmentRepository.delete(assignment.get());
            return true;
        }
        return false;
    }

    // Remove all assignments for an entity
    public void removeAllAssignmentsForEntity(String entityType, String entityId) {
        tagAssignmentRepository.deleteByEntityTypeAndEntityId(entityType, entityId);
    }
}