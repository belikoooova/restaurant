package com.example.app.main.mapper;

import com.example.app.main.data.entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishDtoToDishDataModelMapper {
    Dish map(com.example.api.dto.Dish dishDto);
}
