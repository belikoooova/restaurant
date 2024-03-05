package com.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dish {
    private Long id;

    private String title;

    @Positive
    private Integer price;

    @JsonProperty("cooking_time")
    @Positive
    private Integer cookingTime;

    @JsonProperty("in_menu")
    private Boolean inMenu;

    @PositiveOrZero
    private Integer amount;
}
