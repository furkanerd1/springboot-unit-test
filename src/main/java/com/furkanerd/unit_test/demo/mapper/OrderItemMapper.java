package com.furkanerd.unit_test.demo.mapper;

import com.furkanerd.unit_test.demo.model.dto.OrderItemDto;
import com.furkanerd.unit_test.demo.model.entity.OrderItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {

    @Mapping(source = "product.id",target = "productId")
    @Mapping(source = "product.name",target = "productName")
    OrderItemDto toDto(OrderItem orderItem);

    @InheritInverseConfiguration
    @Mapping(target = "order",ignore = true)
    @Mapping(target = "product",ignore = true)
    OrderItem toEntity(OrderItemDto orderItemDto);

    List<OrderItemDto> toDtoList(List<OrderItem> items);
}
