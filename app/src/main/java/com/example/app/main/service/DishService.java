package com.example.app.main.service;

import com.example.api.dto.Dish;
import com.example.api.dto.EditDishRequest;

import java.util.List;

public interface DishService {
    Dish getById(Long id);

    List<Dish> findAll();

    Dish save(Dish dish);

    Dish edit(Long id, EditDishRequest request);
}
