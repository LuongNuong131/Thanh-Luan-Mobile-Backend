package com.thanhluanmobile.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId; // Có thể null nếu khách mua không cần đăng nhập
    private String customerName;
    private String phone;
    private String email;
    private String address;
    private BigDecimal totalPrice;
    private String status = "PENDING"; // PENDING, CONFIRMED, DELIVERED, CANCELLED

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime = new Date();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}