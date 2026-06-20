package com.microservice.tags.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TagMetaDataDTO(TagDTO tagDto, Long tagId,  @JsonProperty("entityCount") EntityCountDTO entityCountDto, Long count) {

}
