package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.configuration.security.jwt.JwtTokenUtil;
import com.neobis.neoCafe.dto.AdminJwtRequest;
import com.neobis.neoCafe.dto.RegisterDto;
import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.entity.RegistrationCode;
import com.neobis.neoCafe.entity.User;
import com.neobis.neoCafe.repository.RegistrationCodeRepo;
import com.neobis.neoCafe.repository.UserRepo;
import com.neobis.neoCafe.service.AuthorizationService;
import com.neobis.neoCafe.service.EmailService;
import com.neobis.neoCafe.service.RegistrationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final UserRepo userRepo;
    private final RegistrationCodeService codeService;
    private final EmailService emailService;
    private final RegistrationCodeRepo registrationCodeRepo;

    @Override
    public String authByEmail(String email) {
        try {
            Optional<User> userInDB = userRepo.findByEmail(email);

            if (userInDB.isEmpty()) {
                throw new RuntimeException("Пользователя с такой почтой не найдено");
            }
            String activationCode = codeService.generateRegistrationCode(email);
            emailService.sendRegistrationEmail(email, activationCode);
            return "Код активации отправлен на почту!";
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при аутентификации по email");
        }
    }

    @Override
    public String authByCodeAndGetToken(RegistrationCodeRequest codeRequest) {
        try {
            RegistrationCode registrationCode = registrationCodeRepo.findByCode(codeRequest.getCode());
            if (registrationCode == null) {
                throw new RuntimeException("Неверный код регистрации!");
            }
            String userEmail = registrationCode.getEmail();
            Optional<User> userInDB = userRepo.findByEmail(userEmail);
            if (userInDB.isEmpty()) {
                throw new RuntimeException("Пользователя с такой почтой не найдено");
            }
            User user = userInDB.get();
            userService.updateCustomerStatus(user);
            UserDetails userDetails = userService.loadUserByUsername(userEmail);
            return jwtTokenUtil.generateToken(userDetails);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при аутентификации по коду");
        }
    }

    @Override
    public String authenticateAndGetToken(RegisterDto jwtRequest) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());
            return jwtTokenUtil.generateToken(userDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String authAdminAndGetToken(AdminJwtRequest jwtRequest) {
        try {
            authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
            UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());
            return jwtTokenUtil.generateToken(userDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }
    }

}
