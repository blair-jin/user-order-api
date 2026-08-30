package io.github.blairjeon.userorderapi.application.cart.reader;

import io.github.blairjeon.userorderapi.domain.cart.Cart;
import io.github.blairjeon.userorderapi.domain.cart.CartItem;
import io.github.blairjeon.userorderapi.exception.NOT_FOUND.CartItemNotFoundException;
import io.github.blairjeon.userorderapi.exception.NOT_FOUND.CartNotFoundException;
import io.github.blairjeon.userorderapi.repository.cart.CartItemRepository;
import io.github.blairjeon.userorderapi.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartReader {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getCartByUserId(Long userId){
        return cartRepository.findByUserId(userId)
                .orElseThrow(CartNotFoundException::new);
    }

    public Long getCartIdByUserId(Long userId){
        return cartRepository.findIdByUserId(userId)
                .orElseThrow(CartNotFoundException::new);
    }

    public List<CartItem> getItemsByCartId(Long cartId){
        return cartItemRepository.findAllByCartId(cartId);
    }

    public CartItem getItemByCartIdAndId(Long cartId, Long cartItemId){
        return cartItemRepository.findByCartIdAndId(cartId, cartItemId)
                .orElseThrow(CartItemNotFoundException::new);
    }
}