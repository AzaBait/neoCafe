package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.UserDto;
import com.neobis.neoCafe.entity.User;
import com.neobis.neoCafe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final UserService userService;

    @PostMapping("/newEmployee")
    public ResponseEntity<UserDto> createNewEmployee(@RequestBody UserDto userDto) {
        userService.createNewEmployee(userDto);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateEmployee(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.updateEmployee(id, userDto);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getEmployeeById(@PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        return ResponseEntity.ok().body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        String message = userService.deleteUser(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/listOfEmployees")
    public ResponseEntity<List<UserDto>> getAllEmployees() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

}
