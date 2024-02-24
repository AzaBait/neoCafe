package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.UserDto;
import com.neobis.neoCafe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
