package com.thanhluanmobile.controller;

import com.thanhluanmobile.dto.OrderRequest;
import com.thanhluanmobile.dto.MessageResponse;
import com.thanhluanmobile.entity.Order;
import com.thanhluanmobile.entity.OrderItem;
import com.thanhluanmobile.entity.Product;
import com.thanhluanmobile.repository.OrderRepository;
import com.thanhluanmobile.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired OrderRepository orderRepository;
    @Autowired ProductRepository productRepository;

    @PostMapping("/checkout")
    @Transactional
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setPhone(orderRequest.getPhone());
        order.setEmail(orderRequest.getEmail());
        order.setAddress(orderRequest.getAddress());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var itemDto : orderRequest.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + itemDto.getProductId()));

            // Cập nhật tồn kho (Stock)
            if (product.getStockQuantity() < itemDto.getQuantity()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Sản phẩm " + product.getName() + " không đủ số lượng."));
            }
            product.setStockQuantity(product.getStockQuantity() - itemDto.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());

            // FIX LỖI: Lấy giá trị Double an toàn và ép sang BigDecimal
            Double currentPrice = product.getDiscountPrice() != null ? product.getDiscountPrice() : product.getPrice();
            BigDecimal finalPrice = BigDecimal.valueOf(currentPrice != null ? currentPrice : 0.0);

            orderItem.setPrice(finalPrice);

            total = total.add(finalPrice.multiply(BigDecimal.valueOf(itemDto.getQuantity())));
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);
        orderRepository.save(order);

        return ResponseEntity.ok(new MessageResponse("Đặt hàng thành công! Mã đơn: " + order.getOrderId()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }
}