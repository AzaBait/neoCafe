package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.AdminJwtRequest;
import com.neobis.neoCafe.dto.BranchDto;
import com.neobis.neoCafe.dto.JwtResponse;
import com.neobis.neoCafe.dto.WorkScheduleDto;
import com.neobis.neoCafe.entity.Branch;
import com.neobis.neoCafe.mapper.BranchMapper;
import com.neobis.neoCafe.service.AuthorizationService;
import com.neobis.neoCafe.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AuthorizationService authorizationService;
    private final BranchService branchService;
    private final BranchMapper branchMapper;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody AdminJwtRequest jwtRequest) {

        try {
            String token = authorizationService.authAdminAndGetToken(jwtRequest);
            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/newBranch")
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

    @GetMapping("/listOfBranches")
    public ResponseEntity<List<BranchDto>> getListOfBranches() {
        List<BranchDto> branches = branchService.getAll();
        return ResponseEntity.ok().body(branches);
    }
    }

