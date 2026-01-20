package com.microservice.tags.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "tags", indexes = { @Index(name = "idx_tag_name", columnList = "name") }, uniqueConstraints = {
		@UniqueConstraint(name = "uq_tag_name", columnNames = "name"),
		@UniqueConstraint(name = "uq_tag_slug", columnNames = "slug") })
public class TagEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 36, nullable = false, unique = true)
	private String uuid;

	@Column(length = 100, nullable = false, unique = true)
	private String name;

	@Column(length = 120, nullable = false, unique = true)
	private String slug;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(length = 7)
	private String color;

	@Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	public TagEntity() {}

	public TagEntity(String uuid, String name, String slug, String description, String color) {
		this.uuid = uuid;
		this.name = name;
		this.slug = slug;
		this.description = description;
		this.color = color;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
