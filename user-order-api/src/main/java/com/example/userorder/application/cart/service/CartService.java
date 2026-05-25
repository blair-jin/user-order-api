package com.example.userorder.application.cart.service;

import com.example.userorder.application.cart.reader.CartReader;
import com.example.userorder.application.product.reader.ProductReader;
import com.example.userorder.common.exception.ProductNotFoundException;
import com.example.userorder.domain.cart.Cart;
import com.example.userorder.domain.cart.CartItem;
import com.example.userorder.domain.common.vo.Quantity;
import com.example.userorder.domain.product.Product;
import com.example.userorder.dto.cart.CartItemResponse;
import com.example.userorder.repository.cart.CartItemRepository;
import com.example.userorder.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final ProductReader productReader;
    private final CartReader cartReader;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public void addItem(Long userId, Long productId, Quantity orderQuantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.create(userId)));

        Product product = productReader.getProductById(productId);
        addOrIncreaseItem(cart, product.getId(), orderQuantity);
    }

    public Slice<CartItemResponse> getItems(Long userId, Pageable pageable) {
        Cart cart = cartReader.getByUserId(userId);

        Slice<CartItem> items = cartReader.getItemsByCartId(cart.getId(), pageable);

        List<Long> productIds = items.stream().map(CartItem::getProductId).toList();
        List<Product> products = productReader.getProductsByIds(productIds);
        Map<Long, Product> productMap
                = products.stream().collect(Collectors.toMap(Product::getId, product -> product));

        return toCartItemResponses(cart.getId(), productMap, items);
    }

    public CartItemResponse getItemById(Long userId, Long cartItemId) {
        Cart cart = cartReader.getByUserId(userId);
        CartItem item = cartReader.getItemByCartIdAndId(cart.getId(), cartItemId);
        Product product = productReader.getProductById(item.getProductId());

        return CartItemResponse.from(product, item);
    }

    @Transactional
    public void updateItem(Long userId, Long cartItemId, Quantity orderQuantity) {
        Cart cart = cartReader.getByUserId(userId);
        CartItem item = cartReader.getItemByCartIdAndId(cart.getId(), cartItemId);

        item.updateQuantity(orderQuantity);
    }

    @Transactional
    public void deleteItem(Long userId, Long cartItemId) {
        Cart cart = cartReader.getByUserId(userId);
        CartItem item = cartReader.getItemByCartIdAndId(cart.getId(), cartItemId);

        cartItemRepository.delete(item);
    }

    @Transactional
    public void cleanCart(Long userId) {
        Cart cart = cartReader.getByUserId(userId);
        cartItemRepository.deleteAllByCart_Id(cart.getId());
        cartRepository.delete(cart);
    }

    private void addOrIncreaseItem(Cart cart, Long productId, Quantity orderQuantity) {
        cartItemRepository.findByCart_IdAndProductId(cart.getId(), productId)
                .ifPresentOrElse(
                        cartItem -> cartItem.increaseQuantity(orderQuantity),
                        () -> cartItemRepository.save(CartItem.create(cart, productId, orderQuantity))
                );
    }

    private Slice<CartItemResponse> toCartItemResponses(Long cartId, Map<Long, Product> productMap, Slice<CartItem> items) {
        return items.map(item -> {
            Product product = productMap.get(item.getProductId());

            if (product == null) {
                throw new ProductNotFoundException();
            }

            return CartItemResponse.from(product, item);
        });
    }
}