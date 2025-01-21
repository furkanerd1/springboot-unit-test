package com.furkanerd.unit_test.demo.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemDto(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal price,
        String productName
){}
