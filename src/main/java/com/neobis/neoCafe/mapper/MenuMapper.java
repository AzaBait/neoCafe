package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.MenuDto;
import com.neobis.neoCafe.dto.ProductDto;
import com.neobis.neoCafe.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, IngredientMapper.class})
public interface MenuMapper {

//    @Mapping(target = "image", source = "image.url")
//    MenuDto productToMenuDtoWithImage(Product product);

    @Mapping(target = "image", ignore = true)
    MenuDto productToMenuDtoWithoutImage(Product product);

    @Mapping(target = "image", ignore = true)
    Product menuDtoToProduct(MenuDto menuDto);

    List<MenuDto> entitiesToDtos(List<Product> products);

}
