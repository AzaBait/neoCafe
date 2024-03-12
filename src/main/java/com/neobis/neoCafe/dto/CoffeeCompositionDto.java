package com.neobis.neoCafe.dto;

import com.neobis.neoCafe.entity.Ingredient;
import com.neobis.neoCafe.entity.Product;
import com.neobis.neoCafe.entity.Warehouse;
import lombok.Data;

import java.util.List;

@Data
public class CoffeeCompositionDto {

    private IngredientDto ingredientDto;
    private WarehouseDto warehouseDto;
    private Double quantity;
}
