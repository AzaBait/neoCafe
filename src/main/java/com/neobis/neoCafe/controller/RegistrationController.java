package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.CustomerDto;
import com.neobis.neoCafe.dto.RegisterDto;
import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.entity.Customer;
import com.neobis.neoCafe.mapper.CustomerMapper;
import com.neobis.neoCafe.service.CustomerService;
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

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final RegistrationCodeService registrationCodeService;

    @PostMapping("/register")
    public ResponseEntity<String> registerNewCustomer(@Validated @RequestBody RegisterDto registerDto) {
        Customer registeredCustomer = customerMapper.registerDtoToEntity(registerDto);
        customerService.register(registeredCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Код потверждения отправлено на почту!");
    }

    @PostMapping("/verifyRegistrationCode")
    public ResponseEntity<CustomerDto> verifyRegistrationCode(@RequestBody RegistrationCodeRequest registrationCodeRequest) {
        Customer savedCustomer = customerService.save(registrationCodeRequest);
        CustomerDto customerDto = customerMapper.registerEntityToDto(savedCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
    }

}
