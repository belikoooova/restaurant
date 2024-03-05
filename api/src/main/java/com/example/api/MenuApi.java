package com.example.api;

import com.example.api.dto.Dish;

import java.util.List;

public interface MenuApi {
    List<Dish> show();

    Dish addToMenu(Long id);

    Dish deleteFromMenu(Long id);
}
