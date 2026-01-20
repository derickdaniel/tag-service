package com.microservice.tags.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.tags.entity.TagEntity;
import com.microservice.tags.repository.TagRepository;

@Service
public class TagService {

	@Autowired
    private TagRepository tagRepository;


    public TagEntity createTag(TagEntity tag) {
        if (tagRepository.existsByName(tag.getName())) {
            throw new IllegalArgumentException("Tag name already exists");
        }
        if (tagRepository.existsBySlug(tag.getSlug())) {
            throw new IllegalArgumentException("Tag slug already exists");
        }
        return tagRepository.save(tag);
    }
    
    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<TagEntity> getTagBySlug(String slug) {
        return tagRepository.findBySlug(slug);
    }

    public List<TagEntity> searchTags(String keyword) {
        return tagRepository.findByNameContainingIgnoreCase(keyword);
    }
}
