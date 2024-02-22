package com.neobis.neoCafe.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private String username;
    private String password;
    private String firstname;
    private String email;
    private String role;
    private LocalDate birthday;
    private WorkScheduleDto workSchedule;
    private Long branchId;
}
