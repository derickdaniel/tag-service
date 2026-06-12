package com.microservice.tags.dto;

public record TagMetaDataDTO(TagDTO tagDto, long entityId, String entityType, long count) {


}
