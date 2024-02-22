package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.AdminJwtRequest;
import com.neobis.neoCafe.dto.JwtResponse;
import com.neobis.neoCafe.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AuthorizationService authorizationService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody AdminJwtRequest jwtRequest) {

        try {
            String token = authorizationService.authAdminAndGetToken(jwtRequest);
            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}

