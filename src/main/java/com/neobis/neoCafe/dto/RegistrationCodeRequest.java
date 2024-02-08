package com.neobis.neoCafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationCodeRequest {
    private String username;
    private String email;
    private String code;
}
