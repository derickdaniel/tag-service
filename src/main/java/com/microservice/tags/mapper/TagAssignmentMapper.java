package com.microservice.tags.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.microservice.tags.dto.TagAssignmentDTO;
import com.microservice.tags.entity.TagAssignmentEntity;
import com.microservice.tags.entity.TagEntity;

public class TagAssignmentMapper {

	public static TagAssignmentDTO toDTO(TagAssignmentEntity assignment) {
		if (assignment == null) {
			return null;
		}

		return new TagAssignmentDTO(assignment.getId(),
				assignment.getTag() != null ? assignment.getTag().getId() : null, assignment.getEntityType(),
				assignment.getEntityId(), assignment.getCreatedBy(), assignment.getCreatedAt());
	}

	public static TagAssignmentEntity toEntity(TagAssignmentDTO dto, TagEntity tag) {
		if (dto == null) {
			return null;
		}
		TagAssignmentEntity assignment = new TagAssignmentEntity();
		assignment.setId(dto.getId());
		assignment.setTag(tag); // requires Tag entity fetched from DB
		assignment.setEntityType(dto.getEntityType());
		assignment.setEntityId(dto.getEntityId());
		assignment.setCreatedBy(dto.getCreatedBy());
		assignment.setCreatedAt(dto.getCreatedAt());
		return assignment;
	}

	public static List<TagAssignmentDTO> toDTOList(List<TagAssignmentEntity> assignments) {
		if (assignments == null) {
			return null;
		}
		return assignments.stream().map(TagAssignmentMapper::toDTO).collect(Collectors.toList());
	}

	public static List<TagAssignmentEntity> toEntityList(List<TagAssignmentDTO> dtos, TagEntity tag) {
		if (dtos == null) {
			return null;
		}
		return dtos.stream().map(dto -> toEntity(dto, tag)).collect(Collectors.toList());
	}
}