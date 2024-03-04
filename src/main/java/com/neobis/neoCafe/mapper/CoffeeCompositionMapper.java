package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.CoffeeCompositionDto;
import com.neobis.neoCafe.entity.CoffeeComposition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = IngredientMapper.class)
public interface CoffeeCompositionMapper {

    CoffeeCompositionMapper INSTANCE = Mappers.getMapper(CoffeeCompositionMapper.class);

    CoffeeCompositionDto entityToDto(CoffeeComposition composition);
    CoffeeComposition dtoToEntity(CoffeeCompositionDto compositionDto);
}
