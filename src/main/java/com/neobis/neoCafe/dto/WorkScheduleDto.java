package com.neobis.neoCafe.dto;

import com.neobis.neoCafe.enums.DayOfWeek;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class WorkScheduleDto {

    private List<DayOfWeek> dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
