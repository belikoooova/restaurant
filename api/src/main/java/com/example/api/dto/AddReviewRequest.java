package com.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewRequest {
    public static final int MIN_SCORE = 1;
    public static final int MAX_SCORE = 5;

    @JsonProperty("dish_id")
    private Long dishId;

    @Min(MIN_SCORE)
    @Max(MAX_SCORE)
    private Integer score;

    private String comment;
}
