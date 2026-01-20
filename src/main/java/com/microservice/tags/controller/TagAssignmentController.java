package com.microservice.tags.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservice.tags.dto.TagAssignmentDTO;
import com.microservice.tags.entity.TagAssignmentEntity;
import com.microservice.tags.mapper.TagAssignmentMapper;
import com.microservice.tags.service.TagAssignmentService;

import java.util.List;

@RestController
@RequestMapping("/api/tag-assignments")
public class TagAssignmentController {

	private final TagAssignmentService tagAssignmentService;

	public TagAssignmentController(TagAssignmentService tagAssignmentService) {
		this.tagAssignmentService = tagAssignmentService;
	}

	//  Assign a tag to an entity
	@PostMapping
	public ResponseEntity<TagAssignmentDTO> assignTag(@RequestParam Long tagId, @RequestParam String entityType,
			@RequestParam String entityId, @RequestParam Integer createdBy) {
		TagAssignmentEntity assignment = tagAssignmentService.assignTag(tagId, entityType, entityId, createdBy);
		return ResponseEntity.ok(TagAssignmentMapper.toDTO(assignment));
	}

	//  Get all tags for a given entity
	@GetMapping("/{entityType}/{entityId}")
	public ResponseEntity<List<TagAssignmentDTO>> getAssignmentsForEntity(@PathVariable String entityType,
			@PathVariable String entityId) {
		List<TagAssignmentEntity> assignments = tagAssignmentService.getAssignmentsForEntity(entityType, entityId);
		return ResponseEntity.ok(TagAssignmentMapper.toDTOList(assignments));
	}

	//  Get all entities assigned to a tag
	@GetMapping("/tag/{tagId}")
	public ResponseEntity<List<TagAssignmentDTO>> getAssignmentsForTag(@PathVariable Long tagId) {
		List<TagAssignmentEntity> assignments = tagAssignmentService.getAssignmentsForTag(tagId);
		return ResponseEntity.ok(TagAssignmentMapper.toDTOList(assignments));
	}

	//  Remove a specific tag assignment
	@DeleteMapping
	public ResponseEntity<Void> removeAssignment(@RequestParam Long tagId, @RequestParam String entityType,
			@RequestParam String entityId) {
		boolean removed = tagAssignmentService.removeAssignment(tagId, entityType, entityId);
		return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}

	//  Remove all tag assignments for an entity
	@DeleteMapping("/{entityType}/{entityId}")
	public ResponseEntity<Void> removeAllAssignmentsForEntity(@PathVariable String entityType,
			@PathVariable String entityId) {
		tagAssignmentService.removeAllAssignmentsForEntity(entityType, entityId);
		return ResponseEntity.noContent().build();
	}
}