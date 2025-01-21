package com.furkanerd.unit_test.demo.controller;

import com.furkanerd.unit_test.demo.model.OrderStatus;
import com.furkanerd.unit_test.demo.model.dto.OrderDto;
import com.furkanerd.unit_test.demo.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDto>> getCustomerOrders(@PathVariable Long customerId){
        return ResponseEntity.ok(orderService.getOrderByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto){
        return new ResponseEntity<>(
                orderService.createOrder(orderDto),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId,
                                                      @RequestParam OrderStatus status){
       return ResponseEntity.ok(orderService.updateOrderStatus(orderId,status));
    }


}
