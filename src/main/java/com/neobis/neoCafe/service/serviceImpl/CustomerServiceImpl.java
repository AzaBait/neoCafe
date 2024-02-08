package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.entity.Customer;
import com.neobis.neoCafe.entity.Role;
import com.neobis.neoCafe.exception.EmailNotFoundException;
import com.neobis.neoCafe.exception.UsernameNotFoundException;
import com.neobis.neoCafe.repository.CustomerRepo;
import com.neobis.neoCafe.service.CustomerService;
import com.neobis.neoCafe.service.EmailService;
import com.neobis.neoCafe.service.RegistrationCodeService;
import com.neobis.neoCafe.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepo customerRepo;
    private final RoleService roleService;
    private final EmailService emailService;
    private final RegistrationCodeService registrationCodeService;


    @Override
    public void register(Customer customer) {
        Optional<Customer> existingCustomerWithEmail = customerRepo.findByEmail(customer.getEmail());
        if (existingCustomerWithEmail.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Пользователь с такой электронной почтой уже существует!");
        }

        String code = registrationCodeService.generateRegistrationCode(customer.getEmail());
        emailService.sendRegistrationEmail(customer.getEmail(), code);
    }

    @Override
    public Customer save(RegistrationCodeRequest registrationCodeRequest) {

        if (!registrationCodeService.validateCode(registrationCodeRequest.getCode())) {
            throw new RuntimeException("Неверный код регистрации!");
        }

        Role customerRole;
        try {
            customerRole = roleService.findByName("ROLE_CUSTOMER").orElseThrow(
                    () -> new RoleNotFoundException("Роль 'CUSTOMER' не найдена!"));
        } catch (RoleNotFoundException e) {
            throw new RuntimeException(e);
        }
        Customer customer1 = new Customer();
        customer1.setUsername(registrationCodeRequest.getUsername());
        customer1.setEmail(registrationCodeRequest.getEmail());
        customer1.setRole(customerRole);
        return customerRepo.save(customer1);
    }
    @Override
    public Optional<Customer> findByEmail(String email) {
        Optional<Customer> customerWithEmail = customerRepo.findByEmail(email);
        if (customerWithEmail.isEmpty()) {
            throw new EmailNotFoundException("Пользователь с такой почтой " + email + " не найден!");
        }
        return customerWithEmail;
    }
    @Override
    public void updateCustomerStatus(Customer customer) {
        customer.setEnabled(true);
        customerRepo.save(customer);
    }
    @Override
    public Optional<Customer> findByUsername(String username) {
        Optional<Customer> optionalCustomer = customerRepo.findByUsername(username);
        if (optionalCustomer.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с таким именем " + username + " не найден");
        }
        return (optionalCustomer);
    }

    ;
}
