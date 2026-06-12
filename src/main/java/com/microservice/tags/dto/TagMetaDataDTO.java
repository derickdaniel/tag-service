package com.microservice.tags.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TagMetaDataDTO(TagDTO tagDto, Long tagId, Long entityId, String entityType, Long count) {


}
