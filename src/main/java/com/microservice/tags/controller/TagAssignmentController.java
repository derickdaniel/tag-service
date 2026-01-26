package com.microservice.tags.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.tags.dto.TagAssignmentDTO;
import com.microservice.tags.entity.TagAssignmentEntity;
import com.microservice.tags.mapper.TagAssignmentMapper;
import com.microservice.tags.service.TagAssignmentService;

@RestController
@RequestMapping("/api/tag-assignments")
public class TagAssignmentController {

	@Autowired
	private TagAssignmentService tagAssignmentService;

	// Assign a tag to an entity
	// @PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping
	public ResponseEntity<List<TagAssignmentDTO>> assignTag(@RequestBody TagAssignmentDTO requestDTO) {
		List<TagAssignmentEntity> assignment = tagAssignmentService.assignTag(requestDTO);
		return ResponseEntity.ok(TagAssignmentMapper.toDTOList(assignment));
	}

	// Get all tags for a given entity
	@GetMapping("/{entityType}/{entityId}")
	public ResponseEntity<List<TagAssignmentDTO>> getAssignmentsForEntity(@PathVariable String entityType,
			@PathVariable Long entityId) {
		List<TagAssignmentEntity> assignments = tagAssignmentService.getAssignmentsForEntity(entityType, entityId);
		return ResponseEntity.ok(TagAssignmentMapper.toDTOList(assignments));
	}

	// Get all entities assigned to a tag
	@GetMapping("/tag/{tagId}")
	public ResponseEntity<List<TagAssignmentDTO>> getAssignmentsForTag(@PathVariable Long tagId) {
		List<TagAssignmentEntity> assignments = tagAssignmentService.getAssignmentsForTag(tagId);
		return ResponseEntity.ok(TagAssignmentMapper.toDTOList(assignments));
	}

	// Remove a specific tag assignment
	@DeleteMapping
	public ResponseEntity<Void> removeAssignment(@RequestParam Long tagId, @RequestParam String entityType,
			@RequestParam Long entityId) {
		boolean removed = tagAssignmentService.removeAssignment(tagId, entityType, entityId);
		return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}

	// Remove all tag assignments for an entity
	@DeleteMapping("/{entityType}/{entityId}")
	public ResponseEntity<Void> removeAllAssignmentsForEntity(@PathVariable String entityType,
			@PathVariable Long entityId) {
		tagAssignmentService.removeAllAssignmentsForEntity(entityType, entityId);
		return ResponseEntity.noContent().build();
	}
}