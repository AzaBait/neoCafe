package com.neobis.neoCafe.service;

import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.entity.Customer;

import java.util.Optional;

public interface CustomerService {

    void register(Customer customer);

    Customer save(RegistrationCodeRequest registrationCodeRequest);

    Optional<Customer> findByEmail(String email);

    void updateCustomerStatus(Customer customer);

    Optional<Customer> findByUsername(String username);
}
