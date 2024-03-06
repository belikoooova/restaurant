package com.example.app.main.data.repository;

import com.example.app.main.data.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByInMenuTrue();
}
