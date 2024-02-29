package com.neobis.neoCafe.service;

import com.neobis.neoCafe.dto.ProductDto;
import com.neobis.neoCafe.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findByName(String name);

    Optional<Product> getById(Long id);

    ResponseEntity<String> update(Long id, ProductDto product, MultipartFile imageFile);

    ResponseEntity<String> deleteProduct(Long id);

    List<Product> getAllProducts();
    ResponseEntity<Product> save(ProductDto product, MultipartFile imageFile);

}
