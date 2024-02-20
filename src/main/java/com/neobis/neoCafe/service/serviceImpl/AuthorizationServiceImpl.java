package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.configuration.security.jwt.JwtTokenUtil;
import com.neobis.neoCafe.dto.AdminJwtRequest;
import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.entity.RegistrationCode;
import com.neobis.neoCafe.entity.User;
import com.neobis.neoCafe.exception.EmailNotFoundException;
import com.neobis.neoCafe.repository.RegistrationCodeRepo;
import com.neobis.neoCafe.repository.UserRepo;
import com.neobis.neoCafe.service.AuthorizationService;
import com.neobis.neoCafe.service.EmailService;
import com.neobis.neoCafe.service.RegistrationCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final UserRepo userRepo;
    private final RegistrationCodeService codeService;
    private final EmailService emailService;
    private final RegistrationCodeRepo registrationCodeRepo;
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

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
    public String authenticateAndGetToken(RegistrationCodeRequest jwtRequest) {
        try {
            String code = jwtRequest.getCode();
            RegistrationCode registrationCode = registrationCodeRepo.findByCode(code);
            if (registrationCode == null || !code.equals(jwtRequest.getCode())) {
                throw new RuntimeException("Код введен неверно, попробуйте еще раз");
            }
            User user = userService.findByEmail(jwtRequest.getEmail()).orElseThrow(() ->
                    new EmailNotFoundException("Почта указана неверно"));
            userService.updateCustomerStatus(user);
            UserDetails userDetails = userService.loadUserByEmail(jwtRequest.getEmail());
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
            throw new RuntimeException("Данные введены неверно, попробуйте еще раз");
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
