package com.furkanerd.unit_test.demo.model.dto;

import lombok.Builder;

@Builder
public record CustomerDto(
        Long id,
        String name,
        String email
){}
