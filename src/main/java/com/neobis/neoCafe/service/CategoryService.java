package com.neobis.neoCafe.service;

import com.neobis.neoCafe.entity.Category;

import java.util.Optional;

public interface CategoryService {

    Optional<Category> findByName(String name) throws Exception;

    Category createNewCategory(Category category);
    String deleteCategory(Long id);
}
