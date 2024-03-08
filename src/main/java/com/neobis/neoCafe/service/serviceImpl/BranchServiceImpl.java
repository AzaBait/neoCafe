package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.dto.BranchDto;
import com.neobis.neoCafe.entity.Branch;
import com.neobis.neoCafe.entity.Image;
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
    public List<BranchDto> getAll() {
        List<Branch> branches = branchRepo.findAll();
        List<BranchDto> branchDtos = new ArrayList<>();

        for (Branch branch : branches) {
            BranchDto branchDto = branchMapper.branchToBranchDto(branch);
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
    public BranchDto updateBranch(Long id, BranchDto branchDto, MultipartFile file) {
        try {
            Branch branchInDB = branchRepo.findById(id).orElseThrow(() ->
                    new IllegalStateException("Branch with id " + id + " does not exist"));

            Branch updatedBranch = branchMapper.branchDtoToBranch(branchDto);

            if (file != null && !file.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(file);
                String publicId = cloudinaryService.extractPublicId(imageUrl);

                Image branchImage = new Image();
                branchImage.setPublicId(publicId);
                branchImage.setUrl(imageUrl);
                updatedBranch.setImage(branchImage);
            }

            updatedBranch.setId(id);

            Branch savedBranch = branchRepo.save(updatedBranch);

            return branchMapper.branchToBranchDto(savedBranch);
        } catch (Exception e) {
            log.error("Error occurred while updating branch:", e);
            throw new RuntimeException("Could not update branch in the database", e);
        }
    }

}



