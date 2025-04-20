package com.wcc.model.entity;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PostcodeMapper {

    private final ModelMapper modelMapper;

    public PostcodeMapper() {
        this.modelMapper = new ModelMapper();
    }

    public PostcodelatlngDTO toDto(Postcodelatlng entity) {
        return modelMapper.map(entity, PostcodelatlngDTO.class);
    }

    public Postcodelatlng toEntity(PostcodelatlngDTO dto) {
        return modelMapper.map(dto, Postcodelatlng.class);
    }
}