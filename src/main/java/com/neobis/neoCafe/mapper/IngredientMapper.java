package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.CoffeeCompositionDto;
import com.neobis.neoCafe.dto.IngredientDto;
import com.neobis.neoCafe.entity.CoffeeComposition;
import com.neobis.neoCafe.entity.Ingredient;
import org.mapstruct.InheritInverseConfiguration;
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

    @InheritInverseConfiguration
    @Mapping(target = "warehouse", ignore = true)
    @Mapping(target = "isRunningOut", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ingredientType", ignore = true)
    Ingredient dtoToEntity(IngredientDto ingredientDto);

    List<Ingredient> dtosToEntities(List<IngredientDto> ingredientDtos);
    List<IngredientDto> entitiesToDtos(List<Ingredient> ingredients);
    @Mapping(target = "ingredientType", ignore = true)
    @Mapping(target = "warehouseDto", ignore = true)
    @Mapping(target = "isRunningOut", ignore = true)
    @Mapping(target = "name", source = "ingredientDto.name")
    @Mapping(target = "unitOfMeasure", source = "ingredientDto.unitOfMeasure")
    IngredientDto coffeeCompositionDtoToIngredientDto(CoffeeCompositionDto coffeeComposition);

    default List<IngredientDto> coffeeCompositionsToIngredientDtos(List<CoffeeCompositionDto> coffeeCompositions) {
        return coffeeCompositions.stream()
                .map(this::coffeeCompositionDtoToIngredientDto)
                .collect(Collectors.toList());
    }
}
