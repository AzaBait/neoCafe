package com.neobis.neoCafe.dto;

import com.neobis.neoCafe.entity.Category;
import com.neobis.neoCafe.entity.Image;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {

    private Category category;
    private String name;
    private String description;
    private Double price;
    private Image image;
    private List<CoffeeCompositionDto> compositions;
    private BranchDto branchDto;
}
