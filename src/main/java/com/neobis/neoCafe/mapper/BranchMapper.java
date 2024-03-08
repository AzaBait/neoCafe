package com.neobis.neoCafe.mapper;

import com.neobis.neoCafe.dto.BranchDto;
import com.neobis.neoCafe.entity.Branch;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {WorkScheduleMapper.class})
public interface BranchMapper {

    BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);

    @Mapping(source = "image.url", target = "image")
    @Mapping(source = "workSchedule", target = "workScheduleDto")
    BranchDto branchToBranchDto(Branch branch);

    @InheritInverseConfiguration
    @Mapping(source = "image", target = "image.url")
    @Mapping(source = "workScheduleDto", target = "workSchedule")
    Branch branchDtoToBranch(BranchDto branchDto);

    List<BranchDto> branchListToBranchDtoList(List<Branch> branches);

}






