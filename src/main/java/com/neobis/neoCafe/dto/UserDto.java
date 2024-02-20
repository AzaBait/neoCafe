package com.neobis.neoCafe.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {

    private String username;
    private String password;
    private String firstname;
    private String email;
    private Double bonus;
    private LocalDate birthday;
    private List<WorkScheduleDto> workSchedules;
    private BranchDto branch;
    private Boolean enabled = false;
}
