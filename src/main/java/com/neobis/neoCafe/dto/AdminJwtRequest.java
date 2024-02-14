package com.neobis.neoCafe.dto;

import lombok.Data;

@Data
public class AdminJwtRequest {

    private final String email;
    private final String password;
}
