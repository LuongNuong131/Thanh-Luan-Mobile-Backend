package com.thanhluanmobile.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;

    // THÊM MỚI: Phân loại Hãng và RAM
    private String category; // VD: iPhone, Samsung, Oppo...
    private String ram; // VD: 4GB, 8GB, 12GB...

    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private Double discountPrice;
    private Integer stockQuantity;
    private String brand;
    private String model;
    private String storage;
    private String color;
    private String conditionType;
    private Integer warrantyMonths;
    private String image;

    @ElementCollection
    @CollectionTable(name = "product_gallery", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> gallery;

    private String batteryHealth;
    private String accessories;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();
}