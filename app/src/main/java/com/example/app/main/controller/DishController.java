package com.example.app.main.controller;

import com.example.api.dto.Dish;
import com.example.api.dto.EditDishRequest;
import com.example.app.main.service.DishService;
import com.example.api.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController implements DishApi {
    private final DishService dishService;

    @Override
    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<Dish> findAll() {
        return dishService.findAll();
    }

    @Override
    @PostMapping
    @Secured("ROLE_ADMIN")
    public Dish addDish(@RequestBody @Valid Dish dish) {
        return dishService.save(dish);
    }

    @Override
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public Dish editDish(@PathVariable Long id, @RequestBody @Valid EditDishRequest request) {
        return dishService.edit(id, request);
    }
}
