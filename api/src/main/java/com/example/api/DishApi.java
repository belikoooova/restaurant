package com.example.api;

import com.example.api.dto.Dish;
import com.example.api.dto.EditDishRequest;

import java.util.List;

public interface DishApi {
    List<Dish> findAll();

    Dish addDish(Dish dish);

    Dish editDish(Long id, EditDishRequest request);
}
