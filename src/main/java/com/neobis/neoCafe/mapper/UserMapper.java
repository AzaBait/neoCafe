package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.CustomerDto;
import com.neobis.neoCafe.dto.RegisterDto;
import com.neobis.neoCafe.dto.UserDto;
import com.neobis.neoCafe.entity.Role;
import com.neobis.neoCafe.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = BranchMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "role", target = "role", qualifiedByName = "mapStringToRole")
    @Mapping(source = "branchId", target = "branch.id")
    User employeeDtoToEmployee(UserDto userDto);

    @InheritInverseConfiguration
    @Mapping(source = "role", target = "role", qualifiedByName = "mapRoleToString")
    @Mapping(source = "branch.id", target = "branchId")
    UserDto employeeToEmployeeDto(User user);
    @Named("mapStringToRole")
    default Role mapStringToRole(String roleString) {
        return roleString != null ? new Role(roleString) : null;
    }

    @Named("mapRoleToString")
    default String mapRoleToString(Role role) {
        return role != null ? role.getName() : null;
    }

    User registerDtoToEntity(RegisterDto registerDto);

    CustomerDto registerEntityToDto(User user);

}
