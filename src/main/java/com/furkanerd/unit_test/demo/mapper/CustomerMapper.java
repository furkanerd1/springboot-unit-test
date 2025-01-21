package com.furkanerd.unit_test.demo.mapper;

import com.furkanerd.unit_test.demo.model.dto.CustomerDto;
import com.furkanerd.unit_test.demo.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);

    @Mapping(target = "orders",ignore = true)
    Customer toEntity(CustomerDto customerDto);

    List<CustomerDto> toDtoList(List<Customer> customers);
}
