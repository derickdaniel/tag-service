package com.microservice.tags.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservice.tags.dto.TagDTO;
import com.microservice.tags.entity.TagEntity;
import com.microservice.tags.mapper.TagMapper;
import com.microservice.tags.service.TagService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {

	@Autowired
    private TagService tagService;

    // Create a new tag
    @PostMapping
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tag) {
        TagEntity createdTag = tagService.createTag(TagMapper.toEntity(tag));
        return ResponseEntity.ok(TagMapper.toDTO(createdTag));
    }

    // Get tag by slug
    @GetMapping("/{slug}")
    public ResponseEntity<TagDTO> getTagBySlug(@PathVariable String slug) {
        Optional<TagEntity> tag = tagService.getTagBySlug(slug);
        if (tag.isPresent()) {
        	return ResponseEntity.ok(TagMapper.toDTO(tag.get()));
        } else {
        	return ResponseEntity.notFound().build();
        }
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

    // Delete tag by slug
    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteTag(@PathVariable String slug) {
    	return ResponseEntity.unprocessableEntity().build();
    }
}
