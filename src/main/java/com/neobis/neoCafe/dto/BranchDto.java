package com.neobis.neoCafe.dto;

import lombok.Data;

import java.util.List;

@Data
public class BranchDto {

    private Long id;
    private String name;
    private String image;
    private String address;
    private String gisUrl;
    private String phoneNumber;
    private int tableCount;
    private List<WorkScheduleDto> workScheduleDto;
}
