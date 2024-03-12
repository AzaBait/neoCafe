package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.IngredientDto;
import com.neobis.neoCafe.entity.CoffeeComposition;
import com.neobis.neoCafe.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    @Mapping(target = "warehouseDto", ignore = true)
    @Mapping(target = "isRunningOut", ignore = true)
    @Mapping(target = "ingredientType", ignore = true)
    @Mapping(target = "quantity", ignore = true)
    IngredientDto entityToDto(Ingredient ingredient);
    @Mapping(target = "warehouse", ignore = true)
    @Mapping(target = "runningOut", ignore = true)
    @Mapping(target = "id", ignore = true)
    Ingredient dtoToEntity(IngredientDto ingredientDto);

    List<IngredientDto> entitiesToDtos(List<Ingredient> ingredients);

    List<Ingredient> dtosToEntities(List<IngredientDto> ingredientDtos);
    @Mapping(target = "ingredientType", ignore = true)
    @Mapping(target = "warehouseDto", ignore = true)
    @Mapping(target = "isRunningOut", ignore = true)
    @Mapping(target = "name", source = "ingredient.name")
    @Mapping(target = "unitOfMeasure", source = "ingredient.unitOfMeasure")
    IngredientDto coffeeCompositionToIngredientDto(CoffeeComposition coffeeComposition);

    default List<IngredientDto> coffeeCompositionsToIngredientDtos(List<CoffeeComposition> coffeeCompositions) {
        return coffeeCompositions.stream()
                .map(this::coffeeCompositionToIngredientDto)
                .collect(Collectors.toList());
    }
}
