package com.neobis.neoCafe.controller;

import com.neobis.neoCafe.dto.MenuDto;
import com.neobis.neoCafe.dto.ProductDto;
import com.neobis.neoCafe.entity.Product;
import com.neobis.neoCafe.mapper.MenuMapper;
import com.neobis.neoCafe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {


    private final ProductService productService;
    private final MenuMapper menuMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Optional<ProductDto> productOptional = productService.getById(id);
        return productOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
                    produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MenuDto> createProduct(@ModelAttribute MenuDto menuDto,
                                                 @RequestParam("file") MultipartFile file) {
        try {
            Product product = productService.save(menuDto, file);
            MenuDto savedMenuDto = menuMapper.productToMenuDto(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMenuDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @ModelAttribute ProductDto productDto, @RequestParam(value = "file", required = false) MultipartFile file) {
        return productService.update(id, productDto, file);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/productList")
    public ResponseEntity<List<MenuDto>> getAllProducts() {
        List<MenuDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

}
