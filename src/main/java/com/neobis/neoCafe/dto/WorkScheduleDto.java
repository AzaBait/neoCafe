package com.neobis.neoCafe.dto;

import com.neobis.neoCafe.enums.DayOfWeek;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Data
@Getter
@Setter
public class WorkScheduleDto {

    private DayOfWeek dayOfWeek;
    private String startTime;
    private String endTime;
}
