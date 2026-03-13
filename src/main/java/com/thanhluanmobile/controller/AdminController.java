package com.thanhluanmobile.controller;

import com.thanhluanmobile.dto.MessageResponse;
import com.thanhluanmobile.entity.Order;
import com.thanhluanmobile.entity.Product;
import com.thanhluanmobile.repository.OrderRepository;
import com.thanhluanmobile.repository.ProductRepository;
import com.thanhluanmobile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // ==========================================
    // API QUẢN LÝ SẢN PHẨM (CRUD PRODUCTS)
    // ==========================================

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

    // ==========================================
    // API THỐNG KÊ CHO DASHBOARD (ANALYTICS)
    // ==========================================

    @GetMapping("/dashboard/stats")
    public ResponseEntity<?> getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalOrders = orderRepository.count();
        long totalProducts = productRepository.count();

        // Tính tổng doanh thu (chỉ cộng tiền của các đơn hàng đã giao thành công)
        BigDecimal totalRevenue = orderRepository.findAll().stream()
                .filter(o -> "DELIVERED".equals(o.getStatus()))
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", totalUsers);
        stats.put("totalOrders", totalOrders);
        stats.put("totalProducts", totalProducts);
        stats.put("totalRevenue", totalRevenue);

        return ResponseEntity.ok(stats);
    }

    // ==========================================
    // API QUẢN LÝ ĐƠN HÀNG (ORDERS)
    // ==========================================

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody Map<String, String> statusUpdate) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        String newStatus = statusUpdate.get("status");
        order.setStatus(newStatus);
        orderRepository.save(order);

        return ResponseEntity.ok(new MessageResponse("Đã cập nhật trạng thái đơn hàng thành: " + newStatus));
    }
}