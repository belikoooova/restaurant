package com.example.app.main.service;

import com.example.api.dto.Dish;

import java.util.List;

public interface MenuService {
    List<Dish> getMenu();

    Dish addToMenu(Long id);

    Dish deleteFromMenu(Long id);
}
