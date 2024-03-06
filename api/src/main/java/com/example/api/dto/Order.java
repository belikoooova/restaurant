package com.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Order {
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonProperty("is_paid")
    private Boolean isPaid;

    private LocalDate date;

    private List<Dish> dishes;

    @JsonProperty("ready_dishes_amount")
    private Integer readyDishesAmount;
}
