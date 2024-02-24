package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.dto.BranchDto;
import com.neobis.neoCafe.dto.WorkScheduleDto;
import com.neobis.neoCafe.entity.Branch;
import com.neobis.neoCafe.entity.Image;
import com.neobis.neoCafe.entity.WorkSchedule;
import com.neobis.neoCafe.enums.DayOfWeek;
import com.neobis.neoCafe.mapper.BranchMapper;
import com.neobis.neoCafe.mapper.WorkScheduleMapper;
import com.neobis.neoCafe.repository.BranchRepo;
import com.neobis.neoCafe.repository.WorkScheduleRepo;
import com.neobis.neoCafe.service.BranchService;
import com.neobis.neoCafe.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepo branchRepo;
    private final CloudinaryService cloudinaryService;
    private final BranchMapper branchMapper;
    private final WorkScheduleRepo workScheduleRepo;
    private final WorkScheduleMapper workScheduleMapper;

    @Override
    public Branch save(BranchDto branchDto, MultipartFile file) {
        try {
            Branch branch = branchMapper.branchDtoToBranch(branchDto);
            String imageUrl = cloudinaryService.uploadImage(file);
            String publicId = cloudinaryService.extractPublicId(imageUrl);

            Image branchImage = new Image();
            branchImage.setPublicId(publicId);
            branchImage.setUrl(imageUrl);
            branch.setImage(branchImage);
            branch = branchRepo.save(branch);

            return branch;
        } catch (RuntimeException e) {
            log.error("Error occurred while saving branch:", e);
            throw new RuntimeException("Could not save product to the database", e);
        }
    }

    @Override
    public void processWorkScheduleDto(WorkScheduleDto workScheduleDto) {
        List<DayOfWeek> daysOfWeek = workScheduleDto.getDayOfWeek();
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setDayOfWeek(daysOfWeek);
        workScheduleRepo.save(workSchedule);
    }

    @Override
    public List<BranchDto> getAll() {
        List<Branch> branches = branchRepo.findAll();
        List<BranchDto> branchDtos = new ArrayList<>();

        for (Branch branch : branches) {
            BranchDto branchDto = branchMapper.branchToBranchDto(branch);
            WorkScheduleDto workScheduleDto = workScheduleMapper.workScheduleToWorkScheduleDto(branch.getWorkSchedule());
            branchDto.setWorkScheduleDto(workScheduleDto);
            branchDtos.add(branchDto);
        }

        log.info("Found {} branches", branches.size());
        return branchDtos;
    }

    @Override
    public BranchDto getById(Long id) {
        Branch branch = branchRepo.findById(id).orElseThrow(()
                -> new IllegalStateException("Branch with id " + id + " does not exist"));
        return branchMapper.branchToBranchDto(branch);
    }

    @Override
    public ResponseEntity<String> deleteBranch(Long id) {
        Branch branch = branchRepo.findById(id).orElseThrow(()
        -> new IllegalStateException("Branch with id " + id + " does not exist"));
        branchRepo.deleteById(branch.getId());
        return ResponseEntity.ok("Branch with id " + id + " successfully deleted");
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) {
        Branch branchInDB;
        try {
            branchInDB = branchRepo.findById(id).orElseThrow(() ->
                    new IllegalStateException("Branch with id " + id + " does not exist"));
            branchInDB.setName(branchDto.getName());
            branchInDB.setAddress(branchDto.getAddress());
            branchInDB.setGisUrl(branchDto.getGisUrl());
            branchInDB.setPhoneNumber(branchDto.getPhoneNumber());
            branchInDB.setTableCount(branchDto.getTableCount());
            if (branchDto.getImage() != null) {
                Image image = new Image();
                image.setUrl(branchDto.getImage());
                branchInDB.setImage(image);
            }
            branchRepo.save(branchInDB);


            return branchMapper.branchToBranchDto(branchInDB);

        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}



