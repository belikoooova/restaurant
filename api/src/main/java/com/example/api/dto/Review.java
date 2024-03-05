package com.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {
    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("dish_id")
    private Long dishId;

    private Integer score;

    private String comment;
}