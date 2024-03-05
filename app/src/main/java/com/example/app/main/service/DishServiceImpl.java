package com.example.app.main.service;

import com.example.app.main.data.repository.DishRepository;
import com.example.api.dto.Dish;
import com.example.api.dto.EditDishRequest;
import com.example.app.main.exception.DishNotFoundException;
import com.example.app.main.mapper.DishDataModelToDishDtoMapper;
import com.example.app.main.mapper.DishDtoToDishDataModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final DishDataModelToDishDtoMapper dishDataModelToDishDtoMapper;
    private final DishDtoToDishDataModelMapper dishDtoToDishDataModelMapper;

    public Dish getById(Long id) {
        return dishDataModelToDishDtoMapper.map(
                dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id))
        );
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll()
                .stream()
                .map(dishDataModelToDishDtoMapper::map)
                .toList();
    }

    @Override
    public Dish save(Dish dish) {
        return dishDataModelToDishDtoMapper.map(
                dishRepository.save(dishDtoToDishDataModelMapper.map(dish))
        );
    }

    @Override
    public Dish edit(Long id, EditDishRequest request) {
        Dish dish = getById(id);
        Dish editedDish = Dish.builder()
                .id(dish.getId())
                .title(request.getTitle())
                .cookingTime(request.getCookingTime())
                .amount(request.getAmount())
                .price(request.getPrice())
                .inMenu(dish.getInMenu())
                .build();
        return save(editedDish);
    }
}
