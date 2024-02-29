package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.ProductDto;
import com.neobis.neoCafe.entity.Image;
import com.neobis.neoCafe.entity.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CoffeeCompositionMapper.class})
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "image", target = "image", qualifiedByName = "mapImageToString")
    @Mapping(source = "branch.id", target = "branchId")
    ProductDto entityToDto(Product product);

    @Named("mapImageToString")
    default String mapImageToString(Image image) {
        return image != null ? image.getUrl() : null;
        }
    @InheritInverseConfiguration
    @Mapping(source = "image", target = "image", qualifiedByName = "mapStringToImage")
    @Mapping(source = "branchId", target = "branch.id")
    Product dtoToEntity(ProductDto productDto);

    @Named("mapStringToImage")
    default Image mapStringToImage(String imageUrl) {
        if (imageUrl != null) {
            Image image = new Image();
            image.setUrl(imageUrl);
            return image;
        }
        return null;
    }

    List<ProductDto> entitiesToDtos(List<Product> products);

    List<Product> dtosToEntities(List<ProductDto> productDtos);
}
