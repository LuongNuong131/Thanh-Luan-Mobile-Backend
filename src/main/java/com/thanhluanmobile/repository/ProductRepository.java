package com.thanhluanmobile.repository;

import com.thanhluanmobile.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Sẽ bổ sung custom query search ở đây
}