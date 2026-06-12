package com.microservice.tags.controller;

import java.util.List;
import java.util.Map;

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

import com.microservice.tags.dto.TagDTO;
import com.microservice.tags.dto.TagMetaDataDTO;
import com.microservice.tags.entity.TagEntity;
import com.microservice.tags.mapper.TagMapper;
import com.microservice.tags.service.TagAssignmentService;
import com.microservice.tags.service.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController {

	@Autowired
	private TagService tagService;

	@Autowired
	private TagAssignmentService tagAssignmentService;

	// Create a new tag
	@PostMapping
	public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tag) {
		TagEntity createdTag = tagService.createTag(TagMapper.toEntity(tag));
		return ResponseEntity.ok(TagMapper.toDTO(createdTag));
	}

	// Get tag by slug
	@GetMapping("/{slug}")
	public ResponseEntity<TagDTO> getTagBySlug(@PathVariable String slug) {
		TagEntity tag = tagService.getTagBySlug(slug);
		return ResponseEntity.ok(TagMapper.toDTO(tag));
	}

	// Search tags by keyword
	@GetMapping("/search")
	public ResponseEntity<List<TagDTO>> searchTags(@RequestParam String keyword) {
		List<TagEntity> tags = tagService.searchTags(keyword);
		return ResponseEntity.ok(TagMapper.toDTOList(tags));
	}

	// List all tags
	@GetMapping
	public ResponseEntity<List<TagDTO>> getAllTags() {
		List<TagEntity> tags = tagService.getAllTags();
		return ResponseEntity.ok(TagMapper.toDTOList(tags));
	}

	// List all tags by issue id
	@GetMapping("/issue/{issueId}")
	public ResponseEntity<List<TagDTO>> getTagsByIssueId(@PathVariable String issueId) {
		List<TagDTO> tags = tagService.getTagsByIssueId(Long.valueOf(issueId));
		return ResponseEntity.ok(tags);
	}

	// Get all tags by createdBy
	@GetMapping("/user/{createdBy}")
	public ResponseEntity<Map<Long, List<TagDTO>>> getTagsByCreatedBy(@PathVariable Long createdBy) {
		Map<Long, List<TagDTO>> resultMap = tagAssignmentService.getTagsByCreatedBy(createdBy);
		return ResponseEntity.ok(resultMap);
	}

	@GetMapping("/user/{createdBy}/{entityType}/{tagId}")
	public ResponseEntity<Map<Long, List<TagDTO>>> getTagsBytag(@PathVariable Long tagId, @PathVariable Long createdBy,
			@PathVariable String entityType) {
		Map<Long, List<TagDTO>> resultMap = tagAssignmentService.getTagsByTagIdAndByCreatedBy(tagId, createdBy,
				entityType);
		return ResponseEntity.ok(resultMap);
	}

	@GetMapping("/entity/{enttityType}")
	public ResponseEntity<Map<Long, List<TagDTO>>> getTagsByEntityType(@PathVariable String enttityType) {
		Map<Long, List<TagDTO>> resultMap = tagAssignmentService.getTagsByEntityType(enttityType);
		return ResponseEntity.ok(resultMap);
	}

	@GetMapping("/entity/count")
	public ResponseEntity<List<TagMetaDataDTO>> getTotalEntityCountByTag() {
		return ResponseEntity.ok(tagAssignmentService.getTotalEntityCountByTag());
	}

	@GetMapping("/entity/countByEntity")
	public ResponseEntity<List<TagMetaDataDTO>> getTotalEntityCountByTagEntityType() {
		return ResponseEntity.ok(tagAssignmentService.getTotalEntityCountByTagEntityType());
	}

	// Delete tag by slug
	@DeleteMapping("/{slug}")
	public ResponseEntity<Void> deleteTag(@PathVariable String slug) {
		return ResponseEntity.unprocessableEntity().build();
	}

	@GetMapping("/count")
	public ResponseEntity<Long> getTotalCount() {
		return ResponseEntity.ok(tagService.getTotalCount());
	}

}
