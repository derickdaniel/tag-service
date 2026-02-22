package com.microservice.tags.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microservice.tags.entity.TagAssignmentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagAssignmentRepository extends JpaRepository<TagAssignmentEntity, Long> {

	// Find all assignments for a given entity (issue, project, etc.)
	List<TagAssignmentEntity> findByEntityTypeAndEntityId(String entityType, Long entityId);

	// Find all assignments for a given tag
	List<TagAssignmentEntity> findByTag_Id(Long tagId);

	// Check if a tag is already assigned to an entity
	Optional<TagAssignmentEntity> findByTag_IdAndEntityTypeAndEntityId(Long tagId, String entityType, Long entityId);

	// Delete all assignments for a given entity
	void deleteByEntityTypeAndEntityId(String entityType, Long entityId);

	// JPQL: tags for multiple issues (returns TagEntity with its assignment
	// entityId via projection)
	@Query("SELECT ta.entityId AS entityId, ta.tag AS tag FROM TagAssignmentEntity ta WHERE ta.entityType = 'ISSUE' AND ta.createdBy = :createdBy")
	List<Object[]> findTagsByCreatedBy(@Param("createdBy") Long createdBy);
	
	@Query("SELECT ta.entityId AS entityId, ta.tag AS tag FROM TagAssignmentEntity ta WHERE ta.entityType = 'ISSUE' AND ta.createdBy = :createdBy AND ta.tag.id = :tagId")
	List<Object[]> getTagsByTagIdAndByCreatedBy(Long tagId, Long createdBy);
	
	@Query("SELECT ta.entityId AS entityId, ta.tag AS tag FROM TagAssignmentEntity ta WHERE ta.entityType = :entityType")
	List<Object[]> getTagsByEntityType(@Param("entityType") String entityType);

}