package com.neobis.neoCafe.service;

import com.neobis.neoCafe.dto.AdminJwtRequest;
import com.neobis.neoCafe.dto.RegisterDto;
import com.neobis.neoCafe.dto.RegistrationCodeRequest;

public interface AuthorizationService {

    String authByEmail(String email);

    String authByCodeAndGetToken(RegistrationCodeRequest codeRequest);

    String authenticateAndGetToken(RegisterDto jwtRequest) throws Exception;
    String authAdminAndGetToken(AdminJwtRequest jwtRequest) throws Exception;
}
