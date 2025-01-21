package com.furkanerd.unit_test.demo.service;

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
import com.furkanerd.unit_test.demo.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Customer customer;
    private Product product;
    private OrderItemDto orderItemDto;
    private OrderDto orderDto;
    private Order order;
    private OrderItem orderItem;

    @BeforeEach
    void setUp(){
        //setup customer
        customer = new Customer();
        customer.setId(1L);

        //setup product
        product=new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("100.00"));
        product.setStock(10);

        //setup order item dto
        orderItemDto=OrderItemDto.builder()
                .productId(1L)
                .quantity(2)
                .price(new BigDecimal("100.00"))
                .build();

        //setup order dto
        orderDto=OrderDto.builder()
                .customerId(1L)
                .items(Collections.singletonList(orderItemDto))
                .build();

        //setup order item
        orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setPrice(new BigDecimal("100.00"));

        //setup order
        order = new Order();
        order.setCustomer(customer);
        order.setItems(new ArrayList<>(Collections.singletonList(orderItem)));
        order.setTotalAmount(new BigDecimal("200.00"));
    }

    @Test
    @DisplayName("Should create order successfully when valid data is provided")
    void should_create_order_successfully_when_valid_data_is_provided() {
        //given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderMapper.toEntity(orderDto)).thenReturn(order);
        when(productRepository.findByIdWithLock(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        //when
        OrderDto RESULT = orderService.createOrder(orderDto);

        //then
        assertNotNull(RESULT);
        verify(customerRepository).findById(1L);
        verify(productRepository).findByIdWithLock(1L);
        verify(orderRepository).save(any(Order.class));
        verify(productRepository).save(product);
        assertEquals(8,product.getStock());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when customer does not exist")
    void should_throw_ResourceNotFoundException_when_customer_does_not_exist() {
        //given
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        //when & then
        assertThrows(ResourceNotFoundException.class,()->orderService.createOrder(orderDto));
        verify(orderRepository,never()).save(any(Order.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product stock is insufficient")
    void should_throw_ResourceNotFoundException_when_product_stock_is_insufficient() {
        //given
        product.setStock(1);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderMapper.toEntity(orderDto)).thenReturn(order);
        when(productRepository.findByIdWithLock(1L)).thenReturn(Optional.of(product));

        //when & then
        assertThrows(ResourceNotFoundException.class,()->orderService.createOrder(orderDto));
        verify(orderRepository,never()).save(any(Order.class));
    }

    @Test
    @DisplayName("Should update stock quantity when order is created successfully")
    void should_update_stock_quantity_when_order_is_created_successfully() {
        //given
        int start=product.getStock();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderMapper.toEntity(orderDto)).thenReturn(order);
        when(productRepository.findByIdWithLock(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        //when
        orderService.createOrder(orderDto);

        //then
        verify(customerRepository).findById(1L);
        verify(productRepository).findByIdWithLock(1L);
        verify(productRepository).save(any(Product.class));
        assertEquals(start-orderItem.getQuantity(),product.getStock());
    }

    @Test
    @DisplayName("Should calculate correct total amount when creating order")
    void should_calculate_correct_total_amount_when_creating_order(){
        //given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderMapper.toEntity(orderDto)).thenReturn(order);
        when(productRepository.findByIdWithLock(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        //when
        orderService.createOrder(orderDto);

        //then
        verify(productRepository).save(any(Product.class));
        assertEquals(new BigDecimal("400.00"),order.getTotalAmount());
    }

    @Test
    @DisplayName("Should set pending status when creating new order")
    void should_set_pending_status_when_creating_new_order(){
        // given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findByIdWithLock(1L)).thenReturn(Optional.of(product));
        when(orderMapper.toEntity(orderDto)).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // when
        orderService.createOrder(orderDto);

        // then
        assertEquals(OrderStatus.PENDING, order.getStatus());
    }

}
