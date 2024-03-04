package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.MenuDto;
import com.neobis.neoCafe.dto.ProductDto;
import com.neobis.neoCafe.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, IngredientMapper.class})
public interface MenuMapper {

    MenuDto productToMenuDto(Product product);
    Product menuDtoToProduct(MenuDto menuDto);
    List<MenuDto> entitiesToDtos(List<Product> products);
}
