package com.thanhluanmobile.controller;

import com.thanhluanmobile.dto.MessageResponse;
import com.thanhluanmobile.entity.Product;
import com.thanhluanmobile.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        productRepository.save(product);
        return ResponseEntity.ok(new MessageResponse("Thêm sản phẩm thành công!"));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setDiscountPrice(productDetails.getDiscountPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setBrand(productDetails.getBrand());
        product.setModel(productDetails.getModel());
        product.setStorage(productDetails.getStorage());
        product.setColor(productDetails.getColor());
        product.setConditionType(productDetails.getConditionType());
        product.setUpdatedAt(new Date());

        productRepository.save(product);
        return ResponseEntity.ok(new MessageResponse("Cập nhật sản phẩm thành công!"));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa sản phẩm thành công!"));
    }
}