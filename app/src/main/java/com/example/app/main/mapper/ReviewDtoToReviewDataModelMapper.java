package com.example.app.main.mapper;

import com.example.api.dto.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewDtoToReviewDataModelMapper {
    com.example.app.main.data.entity.Review map(Review reviewDto);
}
