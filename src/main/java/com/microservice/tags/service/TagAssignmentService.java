package com.microservice.tags.service;

import com.microservice.tags.exception.BadRequestException;
import com.microservice.tags.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.tags.dto.TagAssignmentDTO;
import com.microservice.tags.dto.TagDTO;
import com.microservice.tags.dto.TagMetaDataDTO;
import com.microservice.tags.entity.TagAssignmentEntity;
import com.microservice.tags.entity.TagEntity;
import com.microservice.tags.mapper.TagAssignmentMapper;
import com.microservice.tags.mapper.TagMapper;
import com.microservice.tags.repository.TagAssignmentRepository;
import com.microservice.tags.repository.TagRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class TagAssignmentService {

	Logger log = LoggerFactory.getLogger(TagAssignmentService.class);

	@Autowired
	private TagAssignmentRepository tagAssignmentRepository;
	@Autowired
	private TagRepository tagRepository;

	// Assign a tag to an entity
	public List<TagAssignmentEntity> assignTag(TagAssignmentDTO tagAsssignDTO) {

		List<String> tagNameList = tagAsssignDTO.getTagNameList();
		List<TagAssignmentEntity> tagAssignmentList = new ArrayList<TagAssignmentEntity>();

		List<TagAssignmentEntity> existingTagAssignmentList = tagAssignmentRepository
				.findByEntityTypeAndEntityId(tagAsssignDTO.getEntityType(), tagAsssignDTO.getEntityId());

		for (String tagName : tagNameList) {
			// 1. Get Tag by Name

			tagName = tagName.toLowerCase().replace(' ', '-');
			log.info("Searching for tag: " + tagName);
			Optional<TagEntity> tagOpt = tagRepository.findByName(tagName.toLowerCase());

			// 2. Check if tag is present
			if (!tagOpt.isEmpty()) {

				log.info("Tag found in db with name: " + tagName);

				// 3. Check if tag is already assigned
				TagEntity tag = tagOpt.get();
				Optional<TagAssignmentEntity> existing = tagAssignmentRepository.findByTag_IdAndEntityTypeAndEntityId(
						tag.getId(), tagAsssignDTO.getEntityType(), tagAsssignDTO.getEntityId());
				if (existing.isPresent()) {
					log.info("Tag Assignent already done for : " + tagName + " | " + tagAsssignDTO.getEntityType()
							+ " | " + tagAsssignDTO.getEntityId());
					tagAssignmentList.add(existing.get());
				} else {
					// 4. Assign Tag if not assigned
					TagAssignmentEntity assignment = new TagAssignmentEntity(tagOpt.get(),
							tagAsssignDTO.getEntityType(), tagAsssignDTO.getEntityId(), tagAsssignDTO.getCreatedBy(),
							LocalDateTime.now());
					tagAssignmentRepository.save(assignment);
					tagAssignmentList.add(assignment);

					log.info("New tag assignment done for : " + tagName + " | " + tagAsssignDTO.getEntityType() + " | "
							+ tagAsssignDTO.getEntityId());
				}
			} else {
				// 5. Create a new tag if not created
				log.info("Tag not found in db with name " + tagName);

				TagDTO newTag = new TagDTO();

				String name = tagName.toLowerCase().replace(' ', '-');

				newTag.setUuid(UUID.randomUUID().toString());
				newTag.setName(name);
				newTag.setSlug(name);
				newTag.setColor(getRandomHexColor());
				newTag.setDescription("Tag for " + tagAsssignDTO.getEntityType());
				newTag.setCreatedAt(LocalDateTime.now());
				TagEntity tagEntity = tagRepository.save(TagMapper.toEntity(newTag));

				// 6. Assign Tag
				TagAssignmentEntity assignment = new TagAssignmentEntity(tagEntity, tagAsssignDTO.getEntityType(),
						tagAsssignDTO.getEntityId(), tagAsssignDTO.getCreatedBy(), LocalDateTime.now());
				tagAssignmentRepository.save(assignment);

				log.info("New tag assignment done for : " + tagName + " | " + tagAsssignDTO.getEntityType() + " | "
						+ tagAsssignDTO.getEntityId());

				tagAssignmentList.add(assignment);
			}

			// removing exiting tags, which are not in new list
			String s = tagName;
			existingTagAssignmentList.removeIf(ta -> ta.getTag().getName().equalsIgnoreCase(s));
		}

		existingTagAssignmentList.forEach(ta -> {
			tagAssignmentRepository.deleteById(ta.getId());
			log.info("deleted tag assignment", ta.getId() + ": " + ta.getTag().getName());
		});

		return tagAssignmentList;
	}

	// Get all tags assigned to an entity
	public List<TagAssignmentEntity> getAssignmentsForEntity(String entityType, Long entityId) {
		return tagAssignmentRepository.findByEntityTypeAndEntityId(entityType, entityId);
	}

	// Get all entities assigned to a tag
	public List<TagAssignmentEntity> getAssignmentsForTag(Long tagId) {
		return tagAssignmentRepository.findByTag_Id(tagId);
	}

	// Multiple issues -> Map<issueId, List<TagDTO>>
	public Map<Long, List<TagDTO>> getTagsByCreatedBy(Long createdBy) {

		List<Object[]> rows = tagAssignmentRepository.findTagsByCreatedBy(createdBy);
		return createTagAssignmentMap(rows);
	}

	public Map<Long, List<TagDTO>> getTagsByTagIdAndByCreatedBy(Long tagId, Long createdBy, String entityType) {

		List<Object[]> rows = tagAssignmentRepository.getTagsByTagIdAndByCreatedBy(tagId, createdBy, entityType);
		return createTagAssignmentMap(rows);
	}

	// Remove a tag assignment
	public boolean removeAssignment(Long tagId, String entityType, Long entityId) {
		if (tagId == null || entityType == null || entityId == null) {
			throw new BadRequestException("Invalid arguments");
		}
		TagAssignmentEntity assignment = tagAssignmentRepository
				.findByTag_IdAndEntityTypeAndEntityId(tagId, entityType, entityId)
				.orElseThrow(() -> new ResourceNotFoundException("TagAssignment not found for tagId=" + tagId
						+ ", entityType=" + entityType + ", entityId=" + entityId));
		tagAssignmentRepository.delete(assignment);
		return true;
	}

	@Transactional
	public void removeAllAssignmentsForEntity(String entityType, Long entityId) {
		tagAssignmentRepository.deleteByEntityTypeAndEntityId(entityType, entityId);
	}

	public Map<Long, List<TagDTO>> getTagsByEntityType(String entityType) {

		List<Object[]> rows = tagAssignmentRepository.getTagsByEntityType(entityType);
		return createTagAssignmentMap(rows);
	}

	public List<TagMetaDataDTO> getTotalEntityCountByTag() {

		List<Object[]> rows = tagAssignmentRepository.getTotalEntityCountByTag();
		return TagAssignmentMapper.createTagMetaDataList(rows);
	}

	public List<TagMetaDataDTO> getTotalEntityCountByTagEntityType() {

		List<Object[]> rows = tagAssignmentRepository.getTotalEntityCountByTagEntityType();
		return TagAssignmentMapper.createTagMetaDataList(rows);
	}

	private String getRandomHexColor() {
		// Generate a random integer between 0 (inclusive) and 0x1000000 (exclusive)
		// 0x1000000 is 16777216 in decimal, which is one more than 0xFFFFFF
		Random random = new Random();
		int nextInt = random.nextInt(0x1000000);

		// Format the integer as a 6-digit hexadecimal string, padding with leading
		// zeros
		String colorCode = String.format("#%06x", nextInt);

		return colorCode;
	}

	private Map<Long, List<TagDTO>> createTagAssignmentMap(List<Object[]> rows) {
		Map<Long, List<TagDTO>> result = new HashMap<>();

		for (Object[] row : rows) {

			Long entityId = (Long) row[0];
			TagEntity tagEntity = (TagEntity) row[1];
			TagDTO dto = TagMapper.toDTO(tagEntity);
			result.computeIfAbsent(entityId, k -> new ArrayList<>()).add(dto);
		}
		return result;
	}

}