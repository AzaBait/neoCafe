package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.dto.UserDto;
import com.neobis.neoCafe.entity.*;
import com.neobis.neoCafe.exception.EmailNotFoundException;
import com.neobis.neoCafe.exception.UsernameNotFoundException;
import com.neobis.neoCafe.mapper.UserMapper;
import com.neobis.neoCafe.repository.BranchRepo;
import com.neobis.neoCafe.repository.RegistrationCodeRepo;
import com.neobis.neoCafe.repository.RoleRepo;
import com.neobis.neoCafe.repository.UserRepo;
import com.neobis.neoCafe.service.EmailService;
import com.neobis.neoCafe.service.RegistrationCodeService;
import com.neobis.neoCafe.service.RoleService;
import com.neobis.neoCafe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepo userRepo;
    private final RoleService roleService;
    private final EmailService emailService;
    private final RegistrationCodeService registrationCodeService;
    private final RegistrationCodeRepo registrationCodeRepo;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;
    private final BranchRepo branchRepo;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public User createNewEmployee(UserDto userDto) {
        try {
            User user = userMapper.employeeDtoToEmployee(userDto);
            if (userDto.getBranchId() != null) {
                Optional<Branch> branchOptional = branchRepo.findById(userDto.getBranchId());

                if (branchOptional.isPresent()) {
                    user.setBranch(branchOptional.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Филиал с указанным идентификатором не найден!");
                }
            }
            WorkSchedule workSchedule = new WorkSchedule();
            workSchedule.setDayOfWeek(userDto.getWorkSchedule().getDayOfWeek());
            workSchedule.setStartTime(userDto.getWorkSchedule().getStartTime());
            workSchedule.setEndTime(userDto.getWorkSchedule().getEndTime());
            workSchedule.setUsers(Collections.singletonList(user));
            user.setWorkSchedule(workSchedule);
            Optional<User> userWithEmail = userRepo.findByEmail(user.getEmail());
            if (userWithEmail.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Пользователь с такой электронной почтой уже существует!");
            }

            if (user.getRole() != null) {
                Optional<Role> roleOptional = roleRepo.findByName(user.getRole().getName());
                Role role = roleOptional.orElseGet(() -> roleRepo.save(user.getRole()));
                user.setRole(role);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepo.save(user);
        } catch (ResponseStatusException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(User user) {
        Optional<User> existingCustomerWithEmail = userRepo.findByEmail(user.getEmail());
        if (existingCustomerWithEmail.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Пользователь с такой электронной почтой уже существует!");
        }

        String code = registrationCodeService.generateRegistrationCode(user.getEmail());
        emailService.sendRegistrationEmail(user.getEmail(), code);
    }

    @Override
    public User save(RegistrationCodeRequest registrationCodeRequest) {
        if (!registrationCodeService.validateCode(registrationCodeRequest.getCode())) {
            throw new RuntimeException("Код введен неверно, попробуйте еще раз");
        }
        RegistrationCode registrationCode = registrationCodeRepo.findByEmail(registrationCodeRequest.getEmail());
        if (registrationCode == null || !registrationCode.getEmail().equals(registrationCodeRequest.getEmail())) {
            throw new RuntimeException("Почта указана неверно");
        }

        Role customerRole;
        try {
            customerRole = roleService.findByName("ROLE_CUSTOMER").orElseThrow(
                    () -> new RoleNotFoundException("Роль 'CUSTOMER' не найдена!"));
        } catch (RoleNotFoundException e) {
            throw new RuntimeException(e);
        }
        User user1 = new User();
        user1.setEmail(registrationCodeRequest.getEmail());
        user1.setRole(customerRole);
        return userRepo.save(user1);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> customerWithEmail = userRepo.findByEmail(email);
        if (customerWithEmail.isEmpty()) {
            throw new EmailNotFoundException("Пользователь с такой почтой " + email + " не найден!");
        }
        return customerWithEmail;
    }

    @Override
    public void updateCustomerStatus(User user) {
        user.setEnabled(true);
        userRepo.save(user);
    }

    @Override
    public boolean isEmailVerified(String username) {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        return optionalUser.map(User::isEnabled).orElse(false);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> optionalCustomer = userRepo.findByUsername(username);
        if (optionalCustomer.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с таким именем " + username + " не найден");
        }
        return (optionalCustomer);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        Optional<User> userOptional = userRepo.findByEmail(email);
        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("Пользователя с такой почтой " + email + " не найдено!"));
        Role role = user.getRole();
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(role.getName()));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
        Optional<User> user = userRepo.findByEmail(email);
        return user.orElseThrow(() -> new EmailNotFoundException
                ("Пользователя с такой почтой " + email + " не найдено!"));
    }

}
