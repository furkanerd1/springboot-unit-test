package com.furkanerd.unit_test.demo.service.impl;

import com.furkanerd.unit_test.demo.exception.ResourceNotFoundException;
import com.furkanerd.unit_test.demo.mapper.OrderMapper;
import com.furkanerd.unit_test.demo.model.OrderStatus;
import com.furkanerd.unit_test.demo.model.dto.OrderDto;
import com.furkanerd.unit_test.demo.model.dto.OrderItemDto;
import com.furkanerd.unit_test.demo.model.entity.Customer;
import com.furkanerd.unit_test.demo.model.entity.Order;
import com.furkanerd.unit_test.demo.model.entity.OrderItem;
import com.furkanerd.unit_test.demo.model.entity.Product;
import com.furkanerd.unit_test.demo.repository.CustomerRepository;
import com.furkanerd.unit_test.demo.repository.OrderRepository;
import com.furkanerd.unit_test.demo.repository.ProductRepository;
import com.furkanerd.unit_test.demo.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Customer customer = customerRepository.findById(orderDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + orderDto.customerId()));
        Order orderToSave=orderMapper.toEntity(orderDto);
        orderToSave.setCustomer(customer);
        orderToSave.setOrderDate(LocalDateTime.now());
        orderToSave.setStatus(OrderStatus.PENDING);
        processOrderItems(orderToSave,orderDto.items());
        //calculate the fee
        BigDecimal total = orderToSave.getItems().stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderToSave.setTotalAmount(total);
        Order savedOrder=orderRepository.save(orderToSave);
        return orderMapper.toDto(savedOrder);
    }

    private void processOrderItems(Order order, List<OrderItemDto> orderItemDtoList) {
        for(OrderItemDto orderItemDto:orderItemDtoList){
            Product product = productRepository.findByIdWithLock(orderItemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + orderItemDto.productId()));
            if(product.getStock() < orderItemDto.quantity()){
                throw new ResourceNotFoundException("Not enough stock for product with id: " + orderItemDto.productId());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(orderItemDto.quantity());
            order.getItems().add(orderItem);

            product.setStock(product.getStock()-orderItemDto.quantity());
            productRepository.save(product);
        }

    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrderByCustomerId(Long customerId) {
        if(!customerRepository.existsById(customerId)){
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orderMapper.toDtoList(orders);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found with id: " + orderId));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }
}
