package com.neobis.neoCafe.service;

import com.neobis.neoCafe.dto.RegisterDto;

public interface AuthorizationService {

    String authenticateAndGetToken(RegisterDto jwtRequest) throws Exception;
}
