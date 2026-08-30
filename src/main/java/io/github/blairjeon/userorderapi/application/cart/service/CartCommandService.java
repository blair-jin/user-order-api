package io.github.blairjeon.userorderapi.application.cart.service;

import io.github.blairjeon.userorderapi.application.cart.command.CartItemCreateCommand;
import io.github.blairjeon.userorderapi.application.cart.reader.CartReader;
import io.github.blairjeon.userorderapi.application.product.reader.ProductReader;
import io.github.blairjeon.userorderapi.domain.cart.Cart;
import io.github.blairjeon.userorderapi.domain.cart.CartItem;
import io.github.blairjeon.userorderapi.domain.order.vo.OrderQuantity;
import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InsufficientStockException;
import io.github.blairjeon.userorderapi.repository.cart.CartItemRepository;
import io.github.blairjeon.userorderapi.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartCommandService {
    private final CartReader cartReader;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductReader productReader;

    @Transactional
    public void addCartItem(Long userId, CartItemCreateCommand command){
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.create(userId);
                    return cartRepository.save(newCart);
                });

        cartItemRepository.findByCartIdAndProductId(cart.getId(), command.productId())
                .ifPresentOrElse(
                        item -> item.increase(command.quantity()),
                        () -> cartItemRepository.save(CartItem.create(cart.getId(), command.productId(), command.quantity()))
                );
    }

    @Transactional
    public void updateCartItem(Long userId, Long cartItemId, OrderQuantity quantity){
        Long cartId = cartReader.getCartIdByUserId(userId);
        CartItem item = cartReader.getItemByCartIdAndId(cartId, cartItemId);

        Integer stock = productReader.getStockById(item.getProductId());

        if(quantity.value() > stock){
            throw new InsufficientStockException();
        }

        item.update(quantity);
    }

    @Transactional
    public void deleteItem(Long userId, Long cartItemId){
        Long cartId = cartReader.getCartIdByUserId(userId);
        CartItem item = cartReader.getItemByCartIdAndId(cartId, cartItemId);

        cartItemRepository.delete(item);
    }

    @Transactional
    public void delete(Long userId){
        Cart cart = cartReader.getCartByUserId(userId);
        cartRepository.delete(cart);
    }
}