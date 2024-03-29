package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.CustomerDto;
import com.neobis.neoCafe.dto.RegisterDto;
import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.entity.User;
import com.neobis.neoCafe.mapper.UserMapper;
import com.neobis.neoCafe.service.UserService;
import com.neobis.neoCafe.service.RegistrationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RegistrationController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<String> registerNewCustomer(@Validated @RequestBody RegisterDto registerDto) {
        User registeredUser = userMapper.registerDtoToEntity(registerDto);
        userService.register(registeredUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Код потверждения отправлено на почту!");
    }

    @PostMapping("/verifyRegistrationCode")
    public ResponseEntity<CustomerDto> verifyRegistrationCode(@RequestBody RegistrationCodeRequest registrationCodeRequest) {
        User savedUser = userService.save(registrationCodeRequest);
        CustomerDto customerDto = userMapper.registerEntityToDto(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
    }

}
