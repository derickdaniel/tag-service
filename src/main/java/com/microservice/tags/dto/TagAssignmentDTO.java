package com.microservice.tags.dto;

import java.time.LocalDateTime;

public class TagAssignmentDTO {

	private Long id;
	private Long tagId; // reference to Tag entity
	private String entityType; // e.g., "issue", "project"
	private String entityId; // UUID of the entity
	private Integer createdBy; // user who created the assignment
	private LocalDateTime createdAt;

	// Constructors
	public TagAssignmentDTO() {
	}

	public TagAssignmentDTO(Long id, Long tagId, String entityType, String entityId, Integer createdBy,
			LocalDateTime createdAt) {
		this.id = id;
		this.tagId = tagId;
		this.entityType = entityType;
		this.entityId = entityId;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}