package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.WarehouseDto;
import com.neobis.neoCafe.entity.Warehouse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

        WarehouseDto fromEntityToDto(Warehouse warehouse);
        @InheritInverseConfiguration
        @Mapping(target = "id", ignore = true)
        Warehouse fromDtoToEntity(WarehouseDto warehouseDto);

        List<WarehouseDto> fromEntitylistToDtoList(List<Warehouse> warehouseList);
}
