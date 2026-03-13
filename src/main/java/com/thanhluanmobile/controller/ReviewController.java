package com.thanhluanmobile.controller;

import com.thanhluanmobile.dto.MessageResponse;
import com.thanhluanmobile.entity.Product;
import com.thanhluanmobile.entity.Review;
import com.thanhluanmobile.entity.User;
import com.thanhluanmobile.repository.ProductRepository;
import com.thanhluanmobile.repository.ReviewRepository;
import com.thanhluanmobile.repository.UserRepository;
import com.thanhluanmobile.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewRepository.findByProduct_ProductIdOrderByCreatedAtDesc(productId));
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<?> addReview(@PathVariable Long productId, @RequestBody Review reviewRequest) {
        // Lấy thông tin user đang đăng nhập
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy User"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy Sản phẩm"));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        review.setReviewerName(user.getFullName());

        reviewRepository.save(review);
        return ResponseEntity.ok(new MessageResponse("Đánh giá của bạn đã được gửi thành công!"));
    }
}