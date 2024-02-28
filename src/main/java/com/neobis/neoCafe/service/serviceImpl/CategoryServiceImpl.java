package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.dto.CategoryDto;
import com.neobis.neoCafe.entity.Category;
import com.neobis.neoCafe.mapper.CategoryMapper;
import com.neobis.neoCafe.repository.CategoryRepo;
import com.neobis.neoCafe.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<CategoryDto> findByName(String name) {
        Optional<Category> productCategory = categoryRepo.findByName(name);
        if (productCategory.isEmpty()) {
            throw new RuntimeException("Категория " + name + " не найдена");
        }
        CategoryDto categoryDto = categoryMapper.categoryToCategoryDto(productCategory.get());
        return Optional.ofNullable(categoryDto);
    }

    @Override
    public Category createNewCategory(CategoryDto categoryDto) {
        try {
            Category category = categoryMapper.categoryDtoToCategory(categoryDto);
            return categoryRepo.save(category);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании новой категории: " + e.getMessage(), e);
        }
    }

    @Override
    public String deleteCategory(Long id) {
        try {
            Optional<Category> categoryOptional = categoryRepo.findById(id);
            if (categoryOptional.isPresent()) {
                categoryRepo.deleteById(id);
                return "Категория успешно удалена";
            } else {
                throw new RuntimeException("Категория с id " + id + " не найдена");
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении категории: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        try {
            List<Category> categories = categoryRepo.findAll();
            return categories.stream().map(categoryMapper::categoryToCategoryDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка категорий: " + e.getMessage(), e);
        }
    }
}
