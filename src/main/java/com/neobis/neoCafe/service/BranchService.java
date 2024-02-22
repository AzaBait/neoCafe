package com.neobis.neoCafe.service;

import com.neobis.neoCafe.dto.BranchDto;
import com.neobis.neoCafe.dto.WorkScheduleDto;
import com.neobis.neoCafe.entity.Branch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BranchService {

    Branch save(BranchDto branchDto, WorkScheduleDto workScheduleDto, MultipartFile file);

    void processWorkScheduleDto(WorkScheduleDto workScheduleDto);

    List<BranchDto> getAll();

    BranchDto getById(Long id);

    ResponseEntity<String> deleteBranch(Long id);

    BranchDto updateBranch(Long id, BranchDto branchDto, WorkScheduleDto workScheduleDto);
}
