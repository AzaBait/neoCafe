package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.AdminJwtRequest;
import com.neobis.neoCafe.dto.JwtResponse;
import com.neobis.neoCafe.dto.RegisterDto;
import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/getCode")
    public ResponseEntity<?> createAuthenticationCode(@RequestBody RegisterDto jwtRequest) {
        String code = authorizationService.authByEmail(jwtRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body("Код потверждения отправлено на почту!");
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCodeAndGetToken(@RequestBody RegistrationCodeRequest codeRequest) {

        String token;

        try {
            token = authorizationService.authenticateAndGetToken(codeRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/employee")
    public ResponseEntity<?> createAuthToken(@RequestBody AdminJwtRequest jwtRequest) {

        try {
            String code = authorizationService.authByEmail(jwtRequest.getEmail());
            return new ResponseEntity<>(new JwtResponse(code), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
