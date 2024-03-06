package com.example.app.main.service;

import java.util.Map;

public interface CookingService {
    void cook(Map<Long, Integer> dishesAmountById, Long orderId);
}
