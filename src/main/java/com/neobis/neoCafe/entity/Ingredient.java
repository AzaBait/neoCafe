package com.neobis.neoCafe.entity;

import com.neobis.neoCafe.enums.IngredientType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private IngredientType ingredientType;
    private Double quantity;
    private boolean isRunningOut;
    private String unitOfMeasure;
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
}
