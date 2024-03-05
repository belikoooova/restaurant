package com.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditDishRequest {
    @PositiveOrZero
    private Integer amount;

    private String title;

    @JsonProperty("cooking_time")
    @Positive
    private Integer cookingTime;

    @Positive
    private Integer price;
}
