package com.thanhluanmobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "reviews")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    private Integer rating; // 1 đến 5 sao

    @Column(columnDefinition = "TEXT")
    private String comment;

    // Tên người đánh giá (lưu lại để front-end hiển thị cho nhanh)
    private String reviewerName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
}