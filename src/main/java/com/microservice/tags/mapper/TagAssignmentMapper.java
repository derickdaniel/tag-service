package com.microservice.tags.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.microservice.tags.dto.EntityCountDTO;
import com.microservice.tags.dto.TagAssignmentDTO;
import com.microservice.tags.dto.TagMetaDataDTO;
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

	public static List<TagMetaDataDTO> createTagMetaDataList(List<Object[]> rows) {

		List<TagMetaDataDTO> tagMetaDataList = new ArrayList<TagMetaDataDTO>();

		for (Object[] row : rows) {

			Long tagId = (Long) row[0];
			Long count = (Long) row[1];

			EntityCountDTO entityCountDto = new EntityCountDTO();

			tagMetaDataList.add(new TagMetaDataDTO(null, tagId, entityCountDto, count));
		}

		return tagMetaDataList;
	}

	public static Map<Long, EntityCountDTO> createTagMetaDataList2(List<Object[]> rows) {

		List<TagMetaDataDTO> tagMetaDataList = new ArrayList<TagMetaDataDTO>();
		Map<Long, EntityCountDTO> entityCountMap = new HashMap<Long, EntityCountDTO>();

		for (Object[] row : rows) {

			Long tagId = (Long) row[0];
			Long count = (Long) row[1];
			String entityType = (String) row[2];

			if (!entityCountMap.containsKey(tagId)) {

				EntityCountDTO entityCountDto = createEntityCountDTO(count, entityType, null);
				entityCountMap.put(tagId, entityCountDto);
				tagMetaDataList.add(new TagMetaDataDTO(null, tagId, entityCountDto, count));
			} else {

				EntityCountDTO entityCountDto = createEntityCountDTO(count, entityType, entityCountMap.get(tagId));
				tagMetaDataList.add(new TagMetaDataDTO(null, tagId, entityCountDto, count));
			}
		}

		return entityCountMap;
	}

	private static EntityCountDTO createEntityCountDTO(Long count, String entityType, EntityCountDTO entityCountDto) {

		if (entityCountDto == null) {
			entityCountDto = new EntityCountDTO(0L, 0L, 0L, 0L);
		}

		switch (entityType) {
		case "ISSUE": {
			entityCountDto.setIssueCount(count);
			break;
		}
		case "GUIDE": {
			entityCountDto.setGuideCount(count);
			break;
		}
		case "INTERVIEW_QA": {
			entityCountDto.setInterviewQACount(count);
			break;
		}
		default:
		}

		entityCountDto.setTotalCount(
				entityCountDto.getIssueCount() + entityCountDto.getGuideCount() + entityCountDto.getInterviewQACount());

		return entityCountDto;
	}
}