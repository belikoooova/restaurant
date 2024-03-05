package com.example.app.main.service;

import com.example.app.main.data.repository.DishRepository;
import com.example.api.dto.Dish;
import com.example.app.main.exception.DishNotInMenuException;
import com.example.app.main.mapper.DishDataModelToDishDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final DishRepository dishRepository;
    private final DishService dishService;
    private final DishDataModelToDishDtoMapper dishDataModelToDishDtoMapper;

    @Override
    public List<Dish> getMenu() {
        return dishRepository.findByInMenuTrue()
                .stream()
                .map(dishDataModelToDishDtoMapper::map)
                .toList();
    }

    @Override
    public Dish addToMenu(Long id) {
        Dish dish = dishService.getById(id);
        dish.setInMenu(true);
        return dishService.save(dish);
    }

    @Override
    public Dish deleteFromMenu(Long id) {
        Dish dish = dishService.getById(id);
        if (!dish.getInMenu()) {
            throw new DishNotInMenuException(id);
        }
        dish.setInMenu(false);
        return dishService.save(dish);
    }
}
