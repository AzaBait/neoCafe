package com.neobis.neoCafe.dto;

import lombok.Data;

@Data
public class MenuDto {

    private String name;
    private String image;
    private CategoryDto category;
    private Double price;
    private IngredientDto composition;
}
