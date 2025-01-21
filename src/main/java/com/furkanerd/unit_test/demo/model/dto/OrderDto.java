package com.furkanerd.unit_test.demo.model.dto;

import com.furkanerd.unit_test.demo.model.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDto(
        Long id,
        Long customerId,
        List<OrderItemDto> items,
        LocalDateTime orderDate,
        OrderStatus status,
        BigDecimal totalAmount
){}
