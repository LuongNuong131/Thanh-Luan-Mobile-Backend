package com.thanhluanmobile.repository;

import com.thanhluanmobile.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser_UserId(Long userId);
    Optional<Wishlist> findByUser_UserIdAndProduct_ProductId(Long userId, Long productId);

    @Transactional
    void deleteByUser_UserIdAndProduct_ProductId(Long userId, Long productId);
}