package com.furkanerd.unit_test.demo.service;

import com.furkanerd.unit_test.demo.model.OrderStatus;
import com.furkanerd.unit_test.demo.model.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getOrderByCustomerId(Long customerId);

    OrderDto updateOrderStatus(Long orderId, OrderStatus status);
}
