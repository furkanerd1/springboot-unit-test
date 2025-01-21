package com.furkanerd.unit_test.demo.mapper;


import com.furkanerd.unit_test.demo.model.dto.OrderDto;
import com.furkanerd.unit_test.demo.model.entity.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "customer.id",target = "customerId")
    OrderDto toDto(Order order);

    @InheritInverseConfiguration
    @Mapping(target = "customer",ignore = true)
    Order toEntity(OrderDto orderDto);

    List<OrderDto> toDtoList(List<Order> orders);
}
