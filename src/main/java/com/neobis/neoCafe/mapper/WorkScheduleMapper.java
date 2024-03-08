package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.WorkScheduleDto;
import com.neobis.neoCafe.entity.WorkSchedule;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface WorkScheduleMapper {

    WorkScheduleMapper INSTANCE = Mappers.getMapper(WorkScheduleMapper.class);

    @Mapping(source = "dayOfWeek", target = "dayOfWeek")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    WorkScheduleDto workScheduleToWorkScheduleDto(WorkSchedule workSchedule);
    @InheritInverseConfiguration
    @Mapping(source = "dayOfWeek", target = "dayOfWeek")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    WorkSchedule workScheduleDtoToWorkSchedule(WorkScheduleDto workScheduleDto);

    List<WorkScheduleDto> workScheduleListToWorkScheduleDtoList(List<WorkSchedule> workSchedules);
    List<WorkSchedule> workScheduleDtoListToWorkScheduleList(List<WorkScheduleDto> workScheduleDtos);

}

