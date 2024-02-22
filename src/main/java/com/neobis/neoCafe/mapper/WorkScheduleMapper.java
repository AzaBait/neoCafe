package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.WorkScheduleDto;
import com.neobis.neoCafe.entity.WorkSchedule;
import com.neobis.neoCafe.enums.DayOfWeek;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;




@Mapper(componentModel = "spring", uses = {BranchMapper.class})
public interface WorkScheduleMapper {

    WorkScheduleMapper INSTANCE = Mappers.getMapper(WorkScheduleMapper.class);
    @Mapping(source = "dayOfWeek", target = "dayOfWeek")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    WorkScheduleDto workScheduleToWorkScheduleDto(WorkSchedule workSchedule);
    @Mapping(source = "dayOfWeek", target = "dayOfWeek")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    WorkSchedule workScheduleDtoToWorkSchedule(WorkScheduleDto workScheduleDto);

    List<WorkScheduleDto> workScheduleListToWorkScheduleDtoList(List<WorkSchedule> workSchedules);

}

