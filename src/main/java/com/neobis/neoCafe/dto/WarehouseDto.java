package com.neobis.neoCafe.dto;

import com.neobis.neoCafe.entity.Branch;
import com.neobis.neoCafe.enums.IngredientType;
import lombok.Data;

import java.util.Date;

@Data
public class WarehouseDto {

    private String itemName;
    private String unitOfMeasure;
    private Integer quantity;
    private IngredientType ingredientType;
    private Integer minLimit;
    private Date arrivalDate;
    private Branch branch;
}
