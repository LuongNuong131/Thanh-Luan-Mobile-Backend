package com.thanhluanmobile.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private BigDecimal discountPrice;
    private Integer stockQuantity = 0;
    private String brand = "Apple";
    private String model;
    private String storage;
    private String color;
    private String conditionType;
    private Integer warrantyMonths = 12;
    private Double rating = 0.0;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    // Sẽ thêm @ManyToOne Category và @OneToMany Images ở phase sau cho đỡ rối vòng lặp JSON
}