package com.example.userorder.repository.cart;

import com.example.userorder.domain.cart.CartItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Slice<CartItem> findByCart_Id(Long cartItemId, Pageable pageable);

    Optional<CartItem> findByCart_IdAndId(Long cartId, Long cartItemId);

    Optional<CartItem> findByCart_IdAndProductId(Long cartId, Long productId);

    List<CartItem> findAllByCart_Id(Long cartId);

    void deleteAllByCart_Id(Long cartId);
}