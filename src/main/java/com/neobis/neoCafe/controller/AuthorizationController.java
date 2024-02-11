package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.JwtResponse;
import com.neobis.neoCafe.dto.RegisterDto;
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
@RequestMapping("/api")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody RegisterDto jwtRequest) {
        try {
            String token = authorizationService.authenticateAndGetToken(jwtRequest);
            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
