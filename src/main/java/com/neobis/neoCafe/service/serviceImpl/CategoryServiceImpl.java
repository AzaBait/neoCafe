package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.entity.Category;
import com.neobis.neoCafe.repository.CategoryRepo;
import com.neobis.neoCafe.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public Optional<Category> findByName(String name) throws Exception {
        Optional<Category> productCategory = categoryRepo.findByName(name);
        if (productCategory.isEmpty()) {
            throw new RuntimeException("Категория " + name + " не найдена");
        }
        return productCategory;
    }

    @Override
    public Category createNewCategory(Category category) {
        return null;
    }

    @Override
    public String deleteCategory(Long id) {
        return null;
    }
}
