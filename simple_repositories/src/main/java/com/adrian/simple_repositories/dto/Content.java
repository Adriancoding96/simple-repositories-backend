package com.adrian.simple_repositories.dto;

import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.file.FileDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/*
 * Marker interface to be implemented by DTOs that can be present in a push request
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = FileDTO.class, name = "file"),
  @JsonSubTypes.Type(value = DirectoryFullDTO.class, name = "directory")
})
public interface Content {

}
