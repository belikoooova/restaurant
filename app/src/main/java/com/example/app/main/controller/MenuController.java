package com.example.app.main.controller;

import com.example.api.MenuApi;
import com.example.api.dto.Dish;
import com.example.app.main.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController implements MenuApi {
    private final MenuService menuService;

    @Override
    @GetMapping
    public List<Dish> show() {
        return menuService.getMenu();
    }

    @Override
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public Dish addToMenu(@PathVariable Long id) {
        return menuService.addToMenu(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public Dish deleteFromMenu(@PathVariable Long id) {
        return menuService.deleteFromMenu(id);
    }
}
