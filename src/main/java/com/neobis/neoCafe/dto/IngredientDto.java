package com.neobis.neoCafe.dto;

import com.neobis.neoCafe.enums.IngredientType;
import lombok.Data;

@Data
public class IngredientDto {

    private String name;
    private IngredientType ingredientType;
    private Double quantity;
    private boolean isRunningOut;
    private String unitOfMeasure;
    private WarehouseDto warehouseDto;
}
