package com.thanhluanmobile.controller;

import com.thanhluanmobile.dto.MessageResponse;
import com.thanhluanmobile.entity.Product;
import com.thanhluanmobile.entity.User;
import com.thanhluanmobile.entity.Wishlist;
import com.thanhluanmobile.repository.ProductRepository;
import com.thanhluanmobile.repository.UserRepository;
import com.thanhluanmobile.repository.WishlistRepository;
import com.thanhluanmobile.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired private WishlistRepository wishlistRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;

    private Long getCurrentUserId() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getMyWishlist() {
        List<Wishlist> wishlists = wishlistRepository.findByUser_UserId(getCurrentUserId());
        List<Product> products = wishlists.stream().map(Wishlist::getProduct).collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> addToWishlist(@PathVariable Long productId) {
        Long userId = getCurrentUserId();
        if (wishlistRepository.findByUser_UserIdAndProduct_ProductId(userId, productId).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Sản phẩm này đã có trong danh sách yêu thích!"));
        }

        User user = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlistRepository.save(wishlist);

        return ResponseEntity.ok(new MessageResponse("Đã thêm vào danh sách yêu thích!"));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long productId) {
        wishlistRepository.deleteByUser_UserIdAndProduct_ProductId(getCurrentUserId(), productId);
        return ResponseEntity.ok(new MessageResponse("Đã xóa khỏi danh sách yêu thích!"));
    }
}