package com.neobis.neoCafe.dto;

import lombok.Data;

@Data
public class BranchDto {

    private Long id;
    private String name;
    private String image;
    private String address;
    private String gisUrl;
    private String phoneNumber;
    private int tableCount;
    private WorkScheduleDto workScheduleDto;
}
