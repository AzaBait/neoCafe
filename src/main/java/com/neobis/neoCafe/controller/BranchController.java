package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.BranchDto;
import com.neobis.neoCafe.dto.WorkScheduleDto;
import com.neobis.neoCafe.entity.Branch;
import com.neobis.neoCafe.mapper.BranchMapper;
import com.neobis.neoCafe.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch")
public class BranchController {

    private final BranchService branchService;
    private final BranchMapper branchMapper;


    @PostMapping(value = "/newBranch", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BranchDto> createNewBranch(@ModelAttribute BranchDto branchDto,
                                                     @RequestParam("file") MultipartFile file,
                                                     @ModelAttribute WorkScheduleDto workScheduleDto) {
        try {
            Branch savedBranch = branchService.save(branchDto, workScheduleDto, file);
            BranchDto savedBranchDto = branchMapper.branchToBranchDto(savedBranch);
            savedBranchDto.setWorkScheduleDto(workScheduleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBranchDto);
        }catch (Exception e) {
            log.error("Error occurred while processing request:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateById(@PathVariable Long id,
                                                @ModelAttribute BranchDto branchDto,
                                                @ModelAttribute WorkScheduleDto workScheduleDto) {
        BranchDto updatedbranchDto = branchService.updateBranch(id, branchDto, workScheduleDto);
        return ResponseEntity.ok(updatedbranchDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id) {
        BranchDto branchDto = branchService.getById(id);
        return ResponseEntity.ok().body(branchDto);
    }

    @GetMapping("/listOfBranches")
    public ResponseEntity<List<BranchDto>> getListOfBranches() {
        List<BranchDto> branches = branchService.getAll();
        return ResponseEntity.ok().body(branches);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBranchById(@PathVariable Long id) {
        return branchService.deleteBranch(id);
    }
}
