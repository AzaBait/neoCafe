package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.IngredientDto;
import com.neobis.neoCafe.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    @Mapping(target = "warehouseDto", ignore = true)
    @Mapping(target = "isRunningOut", ignore = true)
    @Mapping(target = "ingredientType", ignore = true)
    IngredientDto entityToDto(Ingredient ingredient);
    Ingredient dtoToEntity(IngredientDto ingredientDto);

    List<IngredientDto> entitiesToDtos(List<Ingredient> ingredients);

    List<Ingredient> dtosToEntities(List<IngredientDto> ingredientDtos);
}
