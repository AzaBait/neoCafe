package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.UserDto;
import com.neobis.neoCafe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
