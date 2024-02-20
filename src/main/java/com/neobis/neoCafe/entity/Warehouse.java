package com.neobis.neoCafe.entity;

import com.neobis.neoCafe.enums.IngredientType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private String unitOfMeasure;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private IngredientType ingredientType;
    private Integer minLimit;
    private Date arrivalDate;
    @OneToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
