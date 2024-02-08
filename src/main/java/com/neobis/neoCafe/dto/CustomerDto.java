package com.neobis.neoCafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {

    private String username;
    private String email;
    private Double bonus;
}
