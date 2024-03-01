package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.dto.ProductDto;
import com.neobis.neoCafe.entity.Image;
import com.neobis.neoCafe.entity.Product;
import com.neobis.neoCafe.mapper.ProductMapper;
import com.neobis.neoCafe.repository.ProductRepo;
import com.neobis.neoCafe.service.CloudinaryService;
import com.neobis.neoCafe.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CloudinaryService cloudinaryService;
    @Override
    public Optional<Product> findByName(String name) {
        Product product = productRepo.findByName(name).orElseThrow(()
                -> new IllegalStateException("Product with name " + name + " does not exist!"));
        return Optional.ofNullable((product));
    }

    @Override
    public Optional<ProductDto> getById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(()
                -> new IllegalStateException("Product with id " + id + " does not exist!"));
        ProductDto productDto = productMapper.entityToDto(product);
        return Optional.ofNullable(productDto);
    }

    @Override
    public ResponseEntity<String> update(Long id, ProductDto productDto, MultipartFile imageFile) {
        try {
            Optional<Product> optionalProduct = productRepo.findById(id);
            if (optionalProduct.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Product existingProduct = optionalProduct.get();
            Product updatedProduct = productMapper.dtoToEntity(productDto);
            updatedProduct.setId(id);

            if (imageFile != null) {
                String imageUrl = cloudinaryService.uploadImage(imageFile);
                String publicId = cloudinaryService.extractPublicId(imageUrl);

                Image productImage = existingProduct.getImage();
                if (productImage == null) {
                    productImage = new Image();
                }
                productImage.setPublicId(publicId);
                productImage.setUrl(imageUrl);
                existingProduct.setImage(productImage);
            }

            productRepo.save(updatedProduct);
            return ResponseEntity.ok("Product with id " + id + " updated successfully");
        } catch (RuntimeException e) {
            log.error("Error occurred while updating product:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not update product");
        }
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() ->
                new IllegalStateException("Product with id " + id + " does not exist!"));
        productRepo.deleteById(product.getId());
        return ResponseEntity.ok("Product with id " + id + " successfully deleted!");
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return productMapper.entitiesToDtos(products);
    }

    @Override
    public ResponseEntity<Product> save(ProductDto productDto, MultipartFile imageFile) {
        try {
            Product product = productMapper.dtoToEntity(productDto);
            String imageUrl = cloudinaryService.uploadImage(imageFile);
            String publicId = cloudinaryService.extractPublicId(imageUrl);

            Image productImage = new Image();
            productImage.setPublicId(publicId);
            productImage.setUrl(imageUrl);
            product.setImage(productImage);
            product = productRepo.save(product);

            return ResponseEntity.ok(product);
        }catch (RuntimeException e) {
            log.error("Error occurred while saving product:", e);
            throw new RuntimeException("Could not save product to the database", e);
        }

    }
}
