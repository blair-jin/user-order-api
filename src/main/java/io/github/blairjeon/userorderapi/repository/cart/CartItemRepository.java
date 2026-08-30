package io.github.blairjeon.userorderapi.repository.cart;

import io.github.blairjeon.userorderapi.domain.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndId(Long cartId, Long cartItemId);

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    List<CartItem> findAllByCartId(Long cartId);

    void deleteAllByCartId(Long cartId);
}