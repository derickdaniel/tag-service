package com.microservice.tags.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.microservice.tags.dto.TagDTO;
import com.microservice.tags.entity.TagEntity;

public class TagMapper {

	public static TagDTO toDTO(TagEntity tag) {
        if (tag == null) {
            return null;
        }
        return new TagDTO(
                tag.getUuid(),
                tag.getName(),
                tag.getSlug(),
                tag.getDescription(),
                tag.getColor(),
                tag.getCreatedAt());
    }

	public static TagEntity toEntity(TagDTO dto) {
		if (dto == null) {
			return null;
		}
		TagEntity tag = new TagEntity();
		tag.setUuid(dto.getUuid());
		tag.setName(dto.getName());
		tag.setSlug(dto.getSlug());
		tag.setDescription(dto.getDescription());
		tag.setColor(dto.getColor());
		tag.setCreatedAt(dto.getCreatedAt());
		return tag;
	}
	
    public static List<TagDTO> toDTOList(List<TagEntity> tags) {
        if (tags == null) {
            return null;
        }
        return tags.stream()
                   .map(TagMapper::toDTO)
                   .collect(Collectors.toList());
    }

    public static List<TagEntity> toEntityList(List<TagDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                   .map(TagMapper::toEntity)
                   .collect(Collectors.toList());
    }

}