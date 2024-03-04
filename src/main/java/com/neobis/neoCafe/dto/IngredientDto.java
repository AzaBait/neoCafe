package com.neobis.neoCafe.dto;

import com.neobis.neoCafe.enums.IngredientType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class IngredientDto {

    private String name;
    private IngredientType ingredientType;
    private Double quantity;
    private Boolean isRunningOut;
    private String unitOfMeasure;
    private WarehouseDto warehouseDto;
}
