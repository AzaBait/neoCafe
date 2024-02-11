package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.configuration.security.jwt.JwtTokenUtil;
import com.neobis.neoCafe.dto.RegisterDto;
import com.neobis.neoCafe.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    @Override
    public String authenticateAndGetToken(RegisterDto jwtRequest) {
        try {
            final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());
            return jwtTokenUtil.generateToken(userDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private void authenticate(String username) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, null));
        } catch (DisabledException e) {
            throw new Exception("User disabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }
    }

}
