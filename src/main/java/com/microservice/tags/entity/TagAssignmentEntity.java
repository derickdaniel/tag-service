package com.microservice.tags.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tag_assignments", 
		uniqueConstraints = { @UniqueConstraint(name = "uq_tag_entity", columnNames = { "tag_id", "entity_type", "entity_id" }) }, 
		indexes = { @Index(name = "idx_entity", columnList = "entity_type, entity_id"),	@Index(name = "idx_tag", columnList = "tag_id") })
public class TagAssignmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Foreign key to Tag entity
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tag_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tag_assignment_tag"))
	private TagEntity tag;

	@Column(name = "entity_type", length = 50, nullable = false)
	private String entityType;

	@Column(name = "entity_id", nullable = false)
	private Long entityId;

	@Column(name = "created_by", nullable = false)
	private Integer createdBy;

	@Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	public TagAssignmentEntity() {
	}

	public TagAssignmentEntity(TagEntity tag, String entityType, Long entityId, Integer createdBy, LocalDateTime createdAt) {
		this.tag = tag;
		this.entityType = entityType;
		this.entityId = entityId;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TagEntity getTag() {
		return tag;
	}

	public void setTag(TagEntity tag) {
		this.tag = tag;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
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