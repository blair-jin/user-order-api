package com.example.userorder.application.cart.reader;

import com.example.userorder.common.exception.CartItemNotFoundException;
import com.example.userorder.common.exception.CartNotFoundException;
import com.example.userorder.domain.cart.Cart;
import com.example.userorder.domain.cart.CartItem;
import com.example.userorder.repository.cart.CartItemRepository;
import com.example.userorder.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartReader {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public Cart getByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(CartNotFoundException::new);
    }

    public Slice<CartItem> getItemsByCartId(Long cartId, Pageable pageable) {
        return cartItemRepository.findByCart_Id(cartId, pageable);
    }

    public CartItem getItemByCartIdAndId(Long cartId, Long cartItemId) {
        return cartItemRepository.findByCart_IdAndId(cartId, cartItemId)
                .orElseThrow(CartItemNotFoundException::new);
    }

    public List<CartItem> getAllByCartId(Long cartId) {
        return cartItemRepository.findAllByCart_Id(cartId);
    }
}