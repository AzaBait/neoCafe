package com.neobis.neoCafe.service;

import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.dto.UserDto;
import com.neobis.neoCafe.entity.User;
import com.neobis.neoCafe.exception.EmailNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createNewEmployee(UserDto userDto);

    void register(User user);

    User save(RegistrationCodeRequest registrationCodeRequest);

    Optional<User> findByEmail(String email);

    void updateCustomerStatus(User user);

    boolean isEmailVerified(String username);

    Optional<User> findByUsername(String username);

    UserDetails loadUserByEmail(String email) throws EmailNotFoundException;

    User updateEmployee(Long id, UserDto userDto);

    UserDto getById(Long id);

    String deleteUser(Long id);

    List<UserDto> getAllUsers();
}
