package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.MenuDto;
import com.neobis.neoCafe.entity.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, IngredientMapper.class})

public interface MenuMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "compositions", source = "composition")
    @Mapping(target = "image.url", source = "image")
    Product menuDtoToProduct(MenuDto menuDto);
    @InheritInverseConfiguration
    MenuDto productToMenuDto(Product product);

    List<MenuDto> entitiesToDtos(List<Product> products);
    List<Product> dtosToEntities(List<MenuDto> menuDtoList);

//    default List<MenuDto> entitiesToDtos(List<Product> products){
//        return products.stream()
//                .map(this::productToMenuDto)
//                .collect(Collectors.toList());
//    }

}
