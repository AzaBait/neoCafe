package com.neobis.neoCafe.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {

    private String name;
    private String image;
    private CategoryDto category;
    private Double price;
    private List<IngredientDto> composition;
}
