package com.rishi.aihub.features.document.mapper;

import com.rishi.aihub.features.document.dto.DocumentResponse;
import com.rishi.aihub.features.document.entity.DocumentMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "originalFileName", target = "fileName")
    DocumentResponse toResponse(DocumentMetadata entity);

}