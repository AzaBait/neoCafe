package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.CustomerDto;
import com.neobis.neoCafe.dto.RegisterDto;
import com.neobis.neoCafe.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User registerDtoToEntity(RegisterDto registerDto);

    CustomerDto registerEntityToDto(User user);

}
