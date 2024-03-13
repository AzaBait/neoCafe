package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.CoffeeCompositionDto;
import com.neobis.neoCafe.entity.CoffeeComposition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = IngredientMapper.class)
public interface CoffeeCompositionMapper {

    @Mapping(target = "warehouseDto", ignore = true)
    @Mapping(target = "quantity", ignore = true)
    @Mapping(target = "ingredientDto", source = "ingredient")
    CoffeeCompositionDto entityToDto(CoffeeComposition composition);
    @Mapping(target = "warehouse", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ingredient", source = "ingredientDto")
    CoffeeComposition dtoToEntity(CoffeeCompositionDto compositionDto);
}
