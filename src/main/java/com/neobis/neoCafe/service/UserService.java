package com.neobis.neoCafe.service;

import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.entity.User;

import java.util.Optional;

public interface UserService {

    void register(User user);

    User save(RegistrationCodeRequest registrationCodeRequest);

    Optional<User> findByEmail(String email);

    void updateCustomerStatus(User user);

    boolean isEmailVerified(String username);

    Optional<User> findByUsername(String username);
}
