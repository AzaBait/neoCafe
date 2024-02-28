package com.neobis.neoCafe.service;

import com.neobis.neoCafe.dto.CategoryDto;
import com.neobis.neoCafe.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<CategoryDto> findByName(String name) throws Exception;

    Category createNewCategory(CategoryDto categoryDto);

    String deleteCategory(Long id);

    List<CategoryDto> getAllCategories();
}
