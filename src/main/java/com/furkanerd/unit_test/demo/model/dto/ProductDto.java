package com.furkanerd.unit_test.demo.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock
){}
