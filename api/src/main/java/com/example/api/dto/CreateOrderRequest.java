package com.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @JsonProperty("dishes_id")
    private Map<Long, Integer> dishesAmountById;

    private LocalDate date;
}
