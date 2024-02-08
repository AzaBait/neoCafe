package com.neobis.neoCafe.service;

import com.neobis.neoCafe.entity.RegistrationCode;

public interface RegistrationCodeService {


    String generateRegistrationCode(String email);


    boolean validateCode(String code);

    RegistrationCode findByCode(String code);
}
