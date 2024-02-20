package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.ProductDto;
import com.neobis.neoCafe.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto entityToDto(Product product);
    Product dtoToEntity(ProductDto productDto);
}
