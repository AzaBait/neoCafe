package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.dto.RegistrationCodeRequest;
import com.neobis.neoCafe.dto.UserDto;
import com.neobis.neoCafe.entity.*;
import com.neobis.neoCafe.exception.EmailNotFoundException;
import com.neobis.neoCafe.exception.UsernameNotFoundException;
import com.neobis.neoCafe.mapper.UserMapper;
import com.neobis.neoCafe.mapper.WorkScheduleMapper;
import com.neobis.neoCafe.repository.BranchRepo;
import com.neobis.neoCafe.repository.RegistrationCodeRepo;
import com.neobis.neoCafe.repository.RoleRepo;
import com.neobis.neoCafe.repository.UserRepo;
import com.neobis.neoCafe.service.EmailService;
import com.neobis.neoCafe.service.RegistrationCodeService;
import com.neobis.neoCafe.service.RoleService;
import com.neobis.neoCafe.service.UserService;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.*;

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
    private final WorkScheduleMapper workScheduleMapper;


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

    @Override
    public User updateEmployee(Long id, UserDto userDto) {
        try {
            User existingUser = userRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Сотрудник с id " + id + " не найден"));

            existingUser.setUsername(userDto.getUsername());
            existingUser.setPassword(userDto.getPassword());
            existingUser.setFirstname(userDto.getFirstname());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

            if (userDto.getRole() != null) {
                Optional<Role> roleOptional = roleRepo.findByName(userDto.getRole());
                Role role = roleOptional.orElseThrow(() -> new EntityNotFoundException("Роль не найдена: " + userDto.getRole()));
                existingUser.setRole(role);
            }
            if (userDto.getBranchId() != null) {
                Optional<Branch> branchOptional = branchRepo.findById(userDto.getBranchId());
                Branch branch = branchOptional.orElseThrow(() -> new EntityNotFoundException("Филиал с id " + userDto.getBranchId() + " не найден"));
                existingUser.setBranch(branch);
            } else {
                existingUser.setBranch(null);
            }
            if (userDto.getWorkSchedule() != null) {
                WorkSchedule workSchedule = workScheduleMapper.workScheduleDtoToWorkSchedule(userDto.getWorkSchedule());
                existingUser.setWorkSchedule(workSchedule);
            } else {
                existingUser.setWorkSchedule(null);
            }
            return userRepo.save(existingUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при обновлении сотрудника", e);
        }

    }

    @Override
    public UserDto getById(Long id) {

        User user = userRepo.findById(id).orElseThrow(() -> new IllegalStateException("Пользователь с ID " + id + " не найден."));
        return userMapper.employeeToEmployeeDto(user);
    }

    @Override
    public String deleteUser(Long id) {
        try {
            userRepo.deleteById(id);
            return "Пользователь с ID " + id + " успешно удален.";
        } catch (Exception e) {
            return "Ошибка при удалении пользователя с ID " + id + ".";
        }
    }
    @Override
    public List<UserDto> getAllUsers() {
        try {
            List<User> users = userRepo.findAll();
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : users) {
                userDtos.add(UserMapper.INSTANCE.employeeToEmployeeDto(user));
            }
            return userDtos;
        } catch (Exception e) {
            System.err.println("Ошибка при получении всех пользователей: " + e.getMessage());
            return Collections.emptyList();
        }
    }


}
