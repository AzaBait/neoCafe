package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.CustomerDto;
import com.neobis.neoCafe.dto.RegisterDto;
import com.neobis.neoCafe.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer registerDtoToEntity(RegisterDto registerDto);

    CustomerDto registerEntityToDto(Customer customer);

}
