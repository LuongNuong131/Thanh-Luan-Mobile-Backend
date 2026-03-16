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
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private Double discountPrice;
    private Integer stockQuantity;
    private String brand;
    private String model;
    private String storage;
    private String color;
    private String conditionType; // Tình trạng xước xát
    private Integer warrantyMonths;
    private String image; // Ảnh thumbnail chính

    @ElementCollection
    @CollectionTable(name = "product_gallery", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> gallery; // Danh sách ảnh phụ

    // Thông số chi tiết thêm
    private String batteryHealth; // VD: Pin 99%
    private String accessories; // VD: Cáp sạc, ốp lưng

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();
}